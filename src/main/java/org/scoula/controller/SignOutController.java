//package org.scoula.controller;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.scoula.service.UserService;
//import org.scoula.security.util.JwtProcessor;
//import org.scoula.util.TokenUtils;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/v1/signout")
//@RequiredArgsConstructor
//@Log4j2
//public class SignOutController {
//
//    private final TokenUtils tokenUtils;
//    private final JwtProcessor jwtProcessor;
//    private final UserService userService;
//
//    @DeleteMapping("/user/")
//    public ResponseEntity<?> signOut(@RequestHeader("Authorization") String bearerToken) {
//        String accessToken = tokenUtils.extractAccessToken(bearerToken);
//        String username = jwtProcessor.getUsername(accessToken);
//        int userIdx = userService.findUserIdxByUserId(username);
//        userService.deleteUser(userIdx);
//        return ResponseEntity.ok(Map.of("message", "회원 탈퇴 완료!"));
//    }
//}
