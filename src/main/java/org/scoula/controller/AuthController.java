package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.security.dto.MemberDTO;
import org.scoula.service.AuthService;
import org.scoula.service.UserService;
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
    private final UserService userService;

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String bearerToken) {
        try {
            String accessToken = tokenUtils.extractAccessToken(bearerToken);
            authService.logoutWithAccessToken(accessToken);
            return ResponseEntity.ok(Map.of("message", "로그아웃 성공"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("로그아웃 처리 중 서버 오류", e);
            return ResponseEntity.internalServerError().body(Map.of("error", "로그아웃 처리 중 오류 발생"));
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
            log.error("Refresh Token 처리 중 서버 오류", e);
            return ResponseEntity.internalServerError().body(Map.of("error", "Refresh Token 처리 중 오류"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody MemberDTO memberDTO) {
        try {
            userService.signUp(memberDTO);
            return ResponseEntity.ok(Map.of("message", "회원가입 완료"));
        } catch (Exception e) {
            log.error("[signUp] 회원가입 실패", e);
            return ResponseEntity.internalServerError().body(Map.of("message", "회원가입 실패"));
        }
    }
}
