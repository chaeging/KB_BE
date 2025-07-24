package org.scoula.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.security.account.mapper.UserDetailsMapper;
import org.scoula.security.dto.RefreshTokenDTO;
import org.scoula.security.util.JwtProcessor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

    private final JwtProcessor jwtProcessor;
    private final UserDetailsMapper userDetailsMapper;

    public void logoutWithAccessToken(String accessToken) {
        if (!jwtProcessor.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 Access Token");
        }
        String username = jwtProcessor.getUsername(accessToken);
        userDetailsMapper.clearRefreshToken(username);
        log.info("Refresh Token 삭제 완료 - 사용자: {}", username);
    }

    public Map<String, String> refreshAccessToken(String refreshToken) {
        if (!jwtProcessor.validateToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 Refresh Token 입니다");
        }

        String userId = jwtProcessor.getUsername(refreshToken);
        String savedRefreshToken = userDetailsMapper.getRefreshToken(userId);

        if (savedRefreshToken == null || !savedRefreshToken.equals(refreshToken)) {
            throw new IllegalArgumentException("DB에 저장된 Refresh Token과 일치하지 않습니다");}

        String newAccessToken = jwtProcessor.generateAccessToken(userId);
        String newRefreshToken = jwtProcessor.generateRefreshToken(userId);

        RefreshTokenDTO dto = new RefreshTokenDTO();
        dto.setUser_id(userId);
        dto.setJwt_refresh_token(newRefreshToken);
        userDetailsMapper.updateRefreshToken(dto);

        return Map.of(
                "access_token", newAccessToken,
                "refresh_token", newRefreshToken
        );
    }
}
