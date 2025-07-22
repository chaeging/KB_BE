package org.scoula.dto.oauth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KakaoUserInfoDto {
    private Long kakaoId;
    private String nickname;
    private String email;
    private String profileImageUrl;
    private String token;
}
