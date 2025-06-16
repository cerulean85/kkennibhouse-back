//package net.kkennib.house.email.controller;
//
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import lombok.RequiredArgsConstructor;
//import net.kkennib.house.member.service.EmailService;
//import net.kkennib.house.member.dto.MemberResponse;
//import net.kkennib.house.member.service.MemberService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.io.StringWriter;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/email")
//@RequiredArgsConstructor
//public class EmailController {
//
//  private final EmailService emailService;
//  private final MemberService memberService;
//  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EmailController.class);
//  @Autowired
//  private freemarker.template.Configuration freemarkerConfig;
//
//  @Value("${server.domain}")
//  private String serverDomain;
//
//  @PostMapping("/send")
//  public ResponseEntity<String> sendTestEmail(@RequestBody Map<String, String> request) throws TemplateException, IOException {
//    String email = request.get("email");
//
//    MemberResponse memberResponse = memberService.getEmailMemberByAccountType(email, "email").block();
//    Optional<MemberResponse> response = Optional.ofNullable(memberResponse);
//    if (response.isEmpty() || memberResponse.getNo() == 0L) {
//      return ResponseEntity.status(404).body("There is no member registered with the given email.");
//    }
//
//    Long memberNo = memberResponse.getNo();
//    String token = UUID.randomUUID().toString();
//    passwordResetTokenRepository.save(new ResetToken(userId, token, LocalDateTime.now().plusHours(1), false));
//
//    String token = "abcd1234";
//    Map<String, Object> model = new HashMap<>();
//    model.put("logoUrl", serverDomain + "/images/icon/logo_title.svg");
//    model.put("resetUrl", serverDomain + "/update-pwd?token=" + token);
//    model.put("contactUrl", serverDomain + "/contact");
//
//    String htmlContent = processTemplate("reset-password.ftl", model);
//    String textContent = loadTemplate("reset-password.txt");
//
//    emailService.sendEmail(
//            email,
//            "Reset your Stock DEV password",
//            htmlContent,
//            textContent
//    );
//    return ResponseEntity.status(HttpStatus.OK)
//            .body("Email sent successfully to " + email);
//
//  }
//
//
//}
