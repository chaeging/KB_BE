package org.scoula.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.MailConfig;
import org.scoula.config.RootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MailConfig.class, EmailService.class, EmailVerificationService.class, RootConfig.class,RootConfig.class})
@Log4j2
class EmailVerificationServiceTest {

    @Autowired
    private EmailVerificationService emailVerification;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private EmailService emailService;

    @Test
    void verifyCode() {
        String email = "du123kim@naver.com";
        // 이메일로 인증 코드 발송 → 캐시에 저장됨
        String generatedCode = emailService.sendVerificationCode(email);
        System.out.println("발송된 인증 코드: "+generatedCode);

        boolean result = emailVerification.verifyCode(email, generatedCode);
        System.out.println("result(true면 검증된거임)"+result);

        // 캐시가 삭제됐는지 확인
        String cachedCode = cacheManager.getCache("emailVerificationCache").get(email, String.class);
        assertNull(cachedCode, "검증 성공 시 캐시에서 코드가 삭제돼야 함");
    }

    @Test
    void verifyCode_wrongcode() {
        String email = "user@example.com";
        String code = "ABC123";
        cacheManager.getCache("emailVerificationCache").put(email, code);
        boolean result = emailVerification.verifyCode(email, "WRONG123");
        assertFalse(result, "코드가 다르면 false 반환");
        log.info("❌ 인증 코드 검증 실패(코드 불일치): {}", email);
        String cachedCode = cacheManager.getCache("emailVerificationCache").get(email, String.class);
        assertEquals(code, cachedCode, "검증 실패 시 캐시 유지");
    }


}
