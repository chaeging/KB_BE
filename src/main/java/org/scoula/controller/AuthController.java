package org.scoula.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.dto.swagger.Auth.SwaggerPasswordChangeRequestDTO;
import org.scoula.dto.swagger.Auth.SwaggerRefreshTokenRequestDTO;
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
@Api(tags = "인증 API", description = "회원가입, 로그인, 토큰 재발급, 비밀번호 변경, 회원정보 수정 등의 기능 제공")
public class AuthController {

    private final TokenUtils tokenUtils;
    private final AuthService authService;
    private final UserService userService;
    private final JwtProcessor jwtProcessor;

    @DeleteMapping("/logout")
    @ApiOperation(value = "로그아웃", notes = "DB에 저장된 Refresh Token을 삭제하여 로그아웃합니다.")
    @ApiImplicitParam(name = "Authorization", value = "Bearer {JWT}", required = true, paramType = "header", dataType = "string")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그아웃 성공")
    })
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
    @ApiOperation("Access Token 재발급")
    @ApiResponses({
            @ApiResponse(code = 200, message = "토큰 재발급 성공", response = Map.class),
            @ApiResponse(code = 401, message = "Refresh Token이 유효하지 않음")
    })
    public ResponseEntity<?> refresh(
            @ApiParam(value   = "예) { \"refreshToken\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\" }", required= true)
            @RequestBody SwaggerRefreshTokenRequestDTO body) {
        String refreshToken = body.getRefreshToken() == null ? "" : body.getRefreshToken();

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
    @ApiOperation(value = "회원가입", notes = "회원 가입 처리, 사용자 정보를 DB에 저장합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 가입 완료"),
            @ApiResponse(code = 500, message = "회원 가입 실패")
    })
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
    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴 처리 및 DB에서 사용자 삭제")
    @ApiImplicitParam(name = "Authorization", value = "Bearer {JWT}", required = true, paramType = "header", dataType = "string")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 탈퇴 완료")
    })
    public ResponseEntity<?> signOut(@RequestHeader("Authorization") String bearerToken) {
        String accessToken = tokenUtils.extractAccessToken(bearerToken);
        String username = jwtProcessor.getUsername(accessToken);
        userService.deleteUser(username);
        return ResponseEntity.ok(Map.of("message", "회원 탈퇴 완료!"));
    }

    @PutMapping("/password")
    @ApiOperation(value = "비밀번호 변경", notes = "기존 비밀번호를 검증 후 새 비밀번호로 변경합니다.")
    @ApiImplicitParam(name = "Authorization", value = "Bearer {JWT}", required = true, paramType = "header", dataType = "string")
    @ApiResponses({
            @ApiResponse(code = 200, message = "비밀번호 변경 완료")
    })
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String bearerToken,
                                            @RequestBody SwaggerPasswordChangeRequestDTO body) {
        String accessToken = tokenUtils.extractAccessToken(bearerToken);
        String userid = jwtProcessor.getUsername(accessToken);

        String oldPassword = body.getOldPassword() == null ? "" : body.getOldPassword() ;
        String newPassword = body.getNewPassword() == null ? "" : body.getNewPassword();
        userService.updatePassword(userid, oldPassword, newPassword);
        return ResponseEntity.ok(Map.of("message", "비밀번호 변경 완료!"));
    }

    @PutMapping("/update")
    @ApiOperation(value = "회원정보 수정", notes = "회원 정보를 수정합니다 (userId는 수정 불가)")
    @ApiImplicitParam(name = "Authorization", value = "Bearer {JWT}", required = true, paramType = "header", dataType = "string")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원정보 수정 완료"),
            @ApiResponse(code = 400, message = "해당 사용자를 찾을 수 없음")
    })
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String bearerToken,
                                        @RequestBody MemberDTO user) {
        String accessToken = tokenUtils.extractAccessToken(bearerToken);
        String userId = jwtProcessor.getUsername(accessToken);

        Integer usersIdx = userService.findUserIdxByUserId(userId);
        if (usersIdx == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "해당 사용자를 찾을 수 없습니다."));
        }

        log.info("/v1/auth/update 엔드포인트 호출됨");

        user.setUsersIdx(usersIdx);
        user.setUserId(userId);

        userService.updateUser(user);
        log.info("수정 요청 받은 userName: {}", user.getUserName());
        System.out.println(">> PUT 요청 도착: " + user);
        return ResponseEntity.ok(Map.of("message", "회원정보 수정 완료"));
    }
}
