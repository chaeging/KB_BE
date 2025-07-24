package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.service.AuthService;
import org.scoula.util.TokenUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final TokenUtils tokenUtils;
    private final AuthService authService;

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String bearerToken) {
        try {
            String accessToken = tokenUtils.extractAccessToken(bearerToken);
            authService.logoutWithAccessToken(accessToken);
            return ResponseEntity.ok(Map.of("message", "로그아웃 성공"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("로그아웃 처리 중 오류", e);
            return ResponseEntity.status(500).body(Map.of("error", "로그아웃 처리 중 오류 발생"));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");

        try {
            Map<String, String> tokens = authService.refreshAccessToken(refreshToken);
            return ResponseEntity.ok(tokens);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Refresh Token 처리 중 오류", e);
            return ResponseEntity.status(500).body(Map.of("error", "Refresh Token 처리 중 오류"));
        }
    }
}
