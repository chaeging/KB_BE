package org.scoula.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailVerificationService {

    private final EmailService emailService;

    public boolean verifyCode(String email, String inputCode) {

        String cachedCode = emailService.getCachedCode(email);
        boolean result = cachedCode.trim().equals(inputCode.trim());

        if (result) {
            log.info("[verifyCode] 인증 코드 검증 성공: {}", email);
            // 검증 성공 시 캐시에서 코드 삭제
            emailService.clearVerificationCode(email);
        } else {
            log.warn("[verifyCode] 인증 코드 불일치: 입력값={}, 저장된값={}", inputCode, cachedCode);
        }

        return result;
    }
}
