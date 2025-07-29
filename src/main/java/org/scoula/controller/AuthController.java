package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.security.dto.MemberDTO;
import org.scoula.security.util.JwtProcessor;
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
    private final JwtProcessor jwtProcessor;

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

    @DeleteMapping("/signout")
    public ResponseEntity<?> signOut(@RequestHeader("Authorization") String bearerToken) {
        String accessToken = tokenUtils.extractAccessToken(bearerToken);
        String username = jwtProcessor.getUsername(accessToken);
        userService.deleteUser(username);
        return ResponseEntity.ok(Map.of("message", "회원 탈퇴 완료!"));
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String bearerToken,
                                           @RequestBody Map<String, String> body) {
        String accessToken = tokenUtils.extractAccessToken(bearerToken);
        String userid = jwtProcessor.getUsername(accessToken);

        String oldPassword = body.get("oldPassword");
        String newPassword = body.get("newPassword");
        userService.updatePassword(userid,oldPassword,newPassword);
        return ResponseEntity.ok(Map.of("message","비밀번호 변경 완료!"));
    }

    // 회원정보 수정
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String bearerToken,
                                        @RequestBody MemberDTO user) {
        String accessToken = tokenUtils.extractAccessToken(bearerToken);
        String userId = jwtProcessor.getUsername(accessToken);

        // DB에서 users_idx 조회
        Integer usersIdx = userService.findUserIdxByUserId(userId);
        if (usersIdx == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "해당 사용자를 찾을 수 없습니다."));
        }

        log.info("/v1/auth/update 엔드포인트 호출됨");

        user.setUsersIdx(usersIdx);
        //JWT에 있는 userId로 강제 설정
        user.setUserId(userId); // 절대 수정 못함

        userService.updateUser(user);
        log.info("수정 요청 받은 userName: {}", user.getUserName());
        System.out.println(">> PUT 요청 도착: " + user);
        return ResponseEntity.ok(Map.of("message", "회원정보 수정 완료"));
    }
}
