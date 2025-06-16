package net.kkennib.house.member.controller;

import lombok.RequiredArgsConstructor;
import net.kkennib.house.dto.ServiceResponse;
import net.kkennib.house.enums.AccountType;
import net.kkennib.house.member.dto.MemberDto;
import net.kkennib.house.member.service.EmailService;
import net.kkennib.house.member.dto.MemberResponse;
import net.kkennib.house.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static net.kkennib.house.util.ResponseFactory.createErrorResponse;
import static net.kkennib.house.util.ResponseFactory.createSuccessResponse;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberLogInController {

  private final MemberService memberService;
  private final EmailService emailService;

  @PostMapping("/log-in")
  public ServiceResponse<MemberResponse> login(@RequestBody Map<String, String> request) {
    String email = request.get("email");
    String password = request.get("password");
    String accountType = request.get("accountType");
    ServiceResponse<MemberResponse> memberResponse =  memberService.getEmailMemberByPasswordAndAccountType(email, password, accountType);
    return memberResponse;
  }

  @PostMapping("/sign-up")
  public ServiceResponse<MemberResponse> emailSignup(@RequestBody Map<String, String> request) {
    String email = request.get("email");
    String password = request.get("password");
    String accountType = request.get("accountType");
    return memberService.createEmailMember(email, password, accountType);
  }

  @GetMapping("/check-reset-token")
  public ServiceResponse<Boolean> checkResetToken(@RequestParam("token") String token) {
    if (token == null) {
      return createErrorResponse("Member number and token are required.");
    }
    return memberService.validatePasswordResetToken(token);
  }

  @PostMapping("/send-password-reset-email")
  public ServiceResponse<Object> sendTestEmail(@RequestBody Map<String, String> request) {
    String email = request.get("email");
    String accountType = AccountType.EMAIL.getValue();
    MemberDto memberDto = memberService.getEmailMemberByAccountType(email, accountType);
    Long memberNo = memberDto.getNo();
    if (memberNo == 0L) {
      return createErrorResponse("There is no member registered with the given email.");
    }

    String token = memberService.getResetToken(memberNo);
    String htmlContent = memberService.getResetPasswordPageHtmlContent(token);
    String textContent = memberService.getResetPasswordPageTextContent();
    emailService.sendEmail(
            email,
            "Reset your Stock DEV password",
            htmlContent,
            textContent
    );
    return createSuccessResponse(null);
  }

  @PutMapping("/update-password")
  public ServiceResponse<Boolean> updatePassword(@RequestBody Map<String, String> request) {
    Long memberNo = Long.valueOf(request.get("memberNo"));
    String token = request.get("token");
    String newPassword = request.get("newPassword");
    if (token == null || newPassword == null) {
      return createErrorResponse("Token and new password are required.");
    }
    return memberService.updatePassword(memberNo, token, newPassword);
  }
}
