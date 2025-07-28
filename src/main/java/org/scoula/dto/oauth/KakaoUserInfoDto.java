package org.scoula.dto.oauth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KakaoUserInfoDto {
    private Long kakaoId;
    private String email;
    private String name;
    private String birthday;
    private String address;
    private String token;
}
