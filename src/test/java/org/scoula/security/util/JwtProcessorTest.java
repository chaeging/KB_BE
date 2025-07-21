package org.scoula.security.util;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.scoula.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RootConfig.class, SecurityConfig.class })
@Log4j2
class JwtProcessorTest {
    @Autowired
    JwtProcessor jwtProcessor;

    @Test
    void generateToken() {
        String username = "user0";
        String token = jwtProcessor.generateAccessToken(username);
        log.info("token: " + token);
        assertNotNull(token);
    }

    @Test
    void getUsername() {
        String toekn = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ1c2VyMCIsImlhdCI6MTc1MDgyNzI2NiwiZXhwIjoxNzUwODI3NTY2fQ.jVgyjozxJE9UzL51glgRg8nmtuSdlPwyLfucf0z1_-uofRpH7rGDDu-OkiL7xS8n";
        String username = jwtProcessor.getUsername(toekn);
        log.info("username: " + username);
        assertEquals("user0", username);
    }

    @Test
    void validateToken() {
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ1c2VyMCIsImlhdCI6MTc1MDgyNzI2NiwiZXhwIjoxNzUwODI3NTY2fQ.jVgyjozxJE9UzL51glgRg8nmtuSdlPwyLfucf0z1_-uofRpH7rGDDu-OkiL7xS8n";
        boolean isValid=jwtProcessor.validateToken(token);//5분경과후면예외발생
        log.info(isValid);
        assertTrue(isValid);
    }
}