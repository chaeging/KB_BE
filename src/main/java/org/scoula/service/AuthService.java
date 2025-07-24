package org.scoula.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.security.util.JwtProcessor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

    private final JwtProcessor jwtProcessor;
    private final CacheManager cacheManager;

    public void logoutWithAccessToken(String accessToken) {
        if (!jwtProcessor.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 Access Token");
        }

        String username = jwtProcessor.getUsername(accessToken);
        Cache cache = cacheManager.getCache("refreshTokenCache");
        if (cache != null) {
            cache.evict(username);
            log.info("Refresh Token 캐시에서 삭제 완료 - 사용자: {}", username);
        }
    }

    public Map<String, String> refreshAccessToken(String refreshToken) {
        if (!jwtProcessor.validateToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 Refresh Token 입니다");
        }

        String userId = jwtProcessor.getUsername(refreshToken);

        Cache cache = cacheManager.getCache("refreshTokenCache");
        if (cache == null) {
            throw new IllegalStateException("Refresh Token 캐시가 초기화되지 않았습니다");
        }

        String savedRefreshToken = cache.get(userId, String.class);
        if (savedRefreshToken == null || !savedRefreshToken.equals(refreshToken)) {
            throw new IllegalArgumentException("캐시에 저장된 Refresh Token과 일치하지 않습니다");
        }

        String newAccessToken = jwtProcessor.generateAccessToken(userId);
        String newRefreshToken = jwtProcessor.generateRefreshToken(userId);

        cache.put(userId, newRefreshToken);
        log.info("Refresh Token 캐시에 저장 완료 - 사용자: {}", userId);

        return Map.of(
                "access_token", newAccessToken,
                "refresh_token", newRefreshToken
        );
    }
}
