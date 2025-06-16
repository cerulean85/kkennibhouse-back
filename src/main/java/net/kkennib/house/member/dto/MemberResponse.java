package net.kkennib.house.member.dto;

import net.kkennib.house.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
  private Long no;
  private String email;
  private String accountType;
  private String accessToken;
  private String refreshToken;
  public static MemberResponse empty() {
    return new MemberResponse(0L, "", "", "", "");
  }

  public static MemberResponse of(Long memberNo, String email, String accountType) {
    return new MemberResponse(memberNo, email, accountType, "", "");
  }
}