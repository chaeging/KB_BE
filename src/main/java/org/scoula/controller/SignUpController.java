package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.security.dto.MemberDTO;
import org.scoula.service.EmailService;
import org.scoula.service.EmailVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/signup")
@RequiredArgsConstructor
@Log4j2
public class SignUpController {
    private final EmailService emailService;
    private final EmailVerificationService emailVerification;



    @PostMapping("/email")
    public ResponseEntity<?> sendVerificationCode(@RequestBody Map<String, String> request) {
        String email = request.get("user_id");
        emailService.sendVerificationCode(email);
        String cachedCode = emailService.getCachedCode(email);
        log.info("[/v1/signup/emial] 발송 직후 캐시에 저장된 코드: {}", cachedCode);
        return ResponseEntity.ok(Map.of("message","검증 코드 발송 완료!"));
    }

    @PostMapping("/email/verification")
    public ResponseEntity<?> verifyEmailCode(@RequestBody Map<String, String> request) {
        String email = request.get("user_id");
        String inputCode = request.get("code");

        boolean isVerified = emailVerification.verifyCode(email, inputCode);

        if (isVerified) {
            log.info("[verifyEmailCode] 이메일 인증 성공: {}", email);
            return ResponseEntity.ok(Map.of("message", "이메일 인증 성공"));
        } else {
            log.warn("[verifyEmailCode] 이메일 인증 실패: {}", email);
            return ResponseEntity.badRequest().body(Map.of("message", "이메일 인증 실패"));
        }
    }
//    @PostMapping("")
//    public ResponseEntity<?> signUp(@RequestBody MemberDTO memberDTO) {
//
//
//    }


}
