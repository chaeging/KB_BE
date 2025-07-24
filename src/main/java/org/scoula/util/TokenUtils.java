package org.scoula.util;

import org.springframework.stereotype.Component;

@Component
public class TokenUtils {
    private static final String BEARER_PREFIX = "Bearer ";

    public String extractAccessToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            String accessToken = bearerToken.substring(BEARER_PREFIX.length());
            if (accessToken == null || accessToken.isBlank()) {
                throw new IllegalArgumentException("Access Token이 비어있습니다");
            }
            return accessToken;
        } else {
            throw new IllegalArgumentException("Authorization 헤더가 유효하지 않습니다");
        }
    }
}