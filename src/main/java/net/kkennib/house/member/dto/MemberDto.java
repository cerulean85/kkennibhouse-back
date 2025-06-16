package net.kkennib.house.member.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
  private Long no;
  private String email;
  private String accountType;
  private String accessToken;
  private String refreshToken;

  public static MemberDto empty() {
    return new MemberDto(0L, "", "", "", "");
  }

  public static MemberDto of(Long no, String email, String accountType) {
    return new MemberDto(no, email, accountType, "", "");
  }
}