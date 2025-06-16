package net.kkennib.house.member.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kkennib.house.dto.ServiceResponse;
import net.kkennib.house.member.dto.MemberDto;
import net.kkennib.house.member.dto.MemberResponse;
import net.kkennib.house.member.entity.Member;
import net.kkennib.house.member.entity.ResetToken;
import net.kkennib.house.member.mapper.MemberMapper;
import net.kkennib.house.member.repository.MemberRepository;
import net.kkennib.house.member.repository.ResetTokenRepository;
import net.kkennib.house.service.JwtService;
import net.kkennib.house.util.GoogleTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static net.kkennib.house.util.ResponseFactory.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

  private final JwtService jwtService;
  private final GoogleTokenVerifier googleTokenVerifier;
  private final MemberRepository memberRepository;
  private final MemberMapper memberMapper;

  private final ResetTokenRepository tokenRepository;

  @Autowired
  private Configuration freemarkerConfig;

  @Value("${server.domain}") private String serverDomain;

  public ServiceResponse<MemberResponse> getEmailMemberByPasswordAndAccountType(String email, String password, String accountType) {
    MemberDto memberDto = this.getMemberFromEmailPasswordAccountType(email, password, accountType);
    if (memberDto.getNo() == 0L) {
      return createErrorResponse("Member not found or invalid credentials");
    }
    MemberResponse memberResponse = memberMapper.toResponse(memberDto);
    return createSuccessResponse(memberResponse);
  }

  public MemberDto getEmailMemberByAccountType(String email, String accountType) {
    Optional<Member> optionalMember = memberRepository.findByEmailAndAccountType(email, accountType);
    if (optionalMember.isEmpty()) {
      return MemberDto.empty();
    }

    Member member = optionalMember.get();
    return MemberDto.of(member.getNo(), member.getEmail(), member.getAccountType());
  }

  public ServiceResponse<Boolean> validatePasswordResetToken(String token) {

    Optional<ResetToken> optResetToken = tokenRepository.findByToken(token);
    if (optResetToken.isEmpty()) {
      return createSuccessResponse(false);
    }

    ResetToken resetToken = optResetToken.get();
    if (resetToken.isUsed() || resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
      resetToken.setUsed(true);
      tokenRepository.save(resetToken);
      return createSuccessResponse(false);
    }

    return createSuccessResponse(true);
  }

  public ServiceResponse<Boolean> updatePassword(Long memberNo, String token, String newPassword) {
    ServiceResponse<Boolean> tokenResponse = validatePasswordResetToken(token);
    if (!tokenResponse.isSuccess()) {
      return createErrorResponse("Invalid or expired token");
    }

    ServiceResponse<Boolean> response = createResponse(false);
    Optional<Member> optMember = memberRepository.findById(memberNo);
    if (optMember.isEmpty()) {
      response.setMessage("Member not found");
      return response;
    }

    if (newPassword == null || newPassword.isBlank()) {
      response.setMessage("Password cannot be empty");
      return response;
    }

    Member member = optMember.get();
    member.setPassword(newPassword); // 비밀번호 업데이트
    memberRepository.save(member);

    ResetToken resetToken = tokenRepository.findByToken(token).get();
    resetToken.setUsed(true);
    tokenRepository.save(resetToken);

    response.setData(true);
    return response;
  }

  public ServiceResponse<MemberResponse> createEmailMember(String email, String password, String accountType) {
    Member member = memberRepository.save(new Member(email, password, accountType));
    if (member.getNo() == 0L) {
      return createErrorResponse("Failed to create member");
    }
    MemberDto memberDto = MemberDto.of(member.getNo(), member.getEmail(), member.getAccountType());
    memberDto.setAccessToken(this.getAccessToken(email));
    memberDto.setRefreshToken(this.getRefreshToken(email));
    MemberResponse memberResponse = memberMapper.toResponse(memberDto);
    return createSuccessResponse(memberResponse);
  }

  public String getResetToken(Long memberNo) {
    String token = UUID.randomUUID().toString();
    Optional<ResetToken> existingToken = tokenRepository.findByUserId(memberNo);

    if (existingToken.isPresent()) {
      ResetToken resetToken = existingToken.get();
      resetToken.setToken(token);
      resetToken.setExpiresAt(LocalDateTime.now().plusHours(1));
      resetToken.setUsed(false);
      tokenRepository.save(resetToken); // UPDATE
    } else {
      tokenRepository.save(new ResetToken(memberNo, token, LocalDateTime.now().plusHours(1), false)); // INSERT
    }

    return token;
  }

  public String getResetPasswordPageHtmlContent(String token) {
    Map<String, Object> model = new HashMap<>();
    model.put("logoUrl", serverDomain + "/images/icon/logo_title.svg");
    model.put("resetUrl", serverDomain + "/update-pwd?token=" + token);
    model.put("contactUrl", serverDomain + "/contact");
    return this.processTemplate("reset-password.ftl", model);
  }

  public String getResetPasswordPageTextContent() {
    return this.loadTemplate("reset-password.txt");
  }

  private String getAccessToken(String email) {
    return Optional.ofNullable(email)
            .filter(e -> !e.isBlank())
            .map(jwtService::createAccessToken)
            .orElse("");
  }
  private String getRefreshToken(String email) {
    return Optional.ofNullable(email)
            .filter(e -> !e.isBlank())
            .map(jwtService::createRefreshToken)
            .orElse("");
  }

  private MemberDto getMemberFromEmailPasswordAccountType(String email, String password, String accountType) {
    Optional<Member> optionalMember = memberRepository.findByEmailAndPasswordAndAccountType(email, password, accountType);

    Member member = optionalMember.orElseGet(Member::new);
    if (member.getNo() == 0L) {
      return MemberDto.empty();
    }
    MemberDto memberDto = MemberDto.of(member.getNo(), member.getEmail(), member.getAccountType());
    memberDto.setAccessToken(this.getAccessToken(email));
    memberDto.setRefreshToken(this.getRefreshToken(email));
    return memberDto;
  }

  private String processTemplate(String templateName, Map<String, Object> model) {
    try {
      Template template = freemarkerConfig.getTemplate(templateName);
      StringWriter writer = new StringWriter();
      template.process(model, writer);
      return writer.toString();
    } catch(IOException | TemplateException e) {
      log.error("Error processing template: {}", e.getMessage());
      return "Something went wrong.";
    }
  }

  private String loadTemplate(String filename) {
    try {
      ClassPathResource resource = new ClassPathResource("templates/" + filename);
      return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      log.error("Error processing template: {}", e.getMessage());
      return "Something went wrong.";
    }
  }



}
