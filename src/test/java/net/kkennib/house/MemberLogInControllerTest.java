package net.kkennib.house;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import net.kkennib.house.member.controller.MemberLogInController;
import net.kkennib.house.member.dto.MemberResponse;
import net.kkennib.house.member.service.EmailService;
import net.kkennib.house.member.service.MemberService;
import net.kkennib.house.service.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MemberLogInController.class)
public class MemberLogInControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private MemberService memberService;

  @MockitoBean
  private EmailService emailService;

  @MockitoBean
  private JwtService jwtService;

  @Test
  @WithMockUser(roles = "USER")
  @DisplayName("send-password-reset-email - 정상 요청")
  void sendPasswordResetEmail_Success() throws Exception {
    // given
    String testEmail = "zhwan85@gmail.com";
    Long memberNo = 2L;

    MemberResponse memberResponse = new MemberResponse();
    memberResponse.setNo(memberNo);

    when(memberService.getEmailMemberByAccountType(testEmail, "email")).thenReturn(memberResponse);
    when(memberService.getResetToken(memberNo)).thenReturn("reset-token-xyz");
    when(memberService.getResetPasswordPageHtmlContent("reset-token-xyz")).thenReturn("<html>Reset Password Page</html>");
    when(memberService.getResetPasswordPageTextContent()).thenReturn("Reset your password using the link.");

    Map<String, String> request = Map.of("email", testEmail);

    // when & then
    mockMvc.perform(post("/member/send-password-reset-email")
                    .with(csrf()) // CSRF 토큰 추가
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess").value(true));

    verify(emailService).sendEmail(
            testEmail,
            "Reset your Stock DEV password",
            "<html>Reset Password Page</html>",
            "Reset your password using the link."
    );
  }
}