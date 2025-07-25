package org.scoula.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.ServletConfig;
import org.scoula.dto.AptDTO;
import org.scoula.dto.AptResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AptService.class }) // AptService만 테스트
@Log4j2
class AptServiceTest {

    @Autowired
    private AptService aptService;

    @Test
    void fetchAptData() {
        AptResponseDto response = aptService.fetchAptData(1, 1);
        log.info("전체 응답 {}",response);

        if (response != null && response.getData() != null) {
            for (AptDTO apt : response.getData()) {
                log.info("Apt: {} \n", apt);
            }
        }
    }
}
