package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.service.EmailService;
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
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/email")
    public ResponseEntity<?> sendVerificationCode(@RequestBody Map<String, String> request) {
        String email = request.get("user_id");
        emailService.sendVerificationCode(email);
        return ResponseEntity.ok(Map.of("message","검증 코드 발송 완료!"));
    }
}
