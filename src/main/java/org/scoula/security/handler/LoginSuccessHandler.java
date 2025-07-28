package org.scoula.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.security.dto.CustomUser;
import org.scoula.security.dto.AuthResultDTO;
import org.scoula.security.dto.UserInfoDTO;
import org.scoula.security.util.JsonResponse;
import org.scoula.security.util.JwtProcessor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProcessor jwtProcessor;
    private final CacheManager cacheManager;

    private AuthResultDTO makeAuthResult(CustomUser user) {
        String username = user.getUsername();
        log.info("로그인 성공!!! username = {}", username);
        String accessToken = jwtProcessor.generateAccessToken(username);
        String refreshToken = jwtProcessor.generateRefreshToken(username);

        Cache refreshTokenCache = cacheManager.getCache("refreshTokenCache");
        if (refreshTokenCache != null) {
            refreshTokenCache.put(username, refreshToken);
            log.info("Refresh Token 캐시에 저장 완료: {}", refreshToken);
        } else {
            log.warn("refreshTokenCache가 초기화되지 않았습니다.");
        }

        return new AuthResultDTO(accessToken, refreshToken, UserInfoDTO.of(user.getMember()));
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomUser user = (CustomUser) authentication.getPrincipal();
        AuthResultDTO result = makeAuthResult(user);
        JsonResponse.send(response, result);
        log.info("로그인 성공 - AccessToken, RefreshToken 발급 및 응답 완료");
    }
}
