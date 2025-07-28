package org.scoula.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.scoula.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AptService.class, RootConfig.class }) // AptService만 테스트
@Log4j2
class
AptServiceTest {

    @Autowired
    private AptService aptService;

    @Test
    void fetchAptData() {
        AptResponseDTO response = aptService.fetchAptData(1, 1);
        log.info("전체 응답 {}",response);

        if (response != null && response.getData() != null) {
            for (AptDTO apt : response.getData()) {
                log.info("Apt: {} \n", apt);
            }
        }
    }

    @Test
    void getAllAptData() {
        AptResponseDTO response = aptService.fetchAptData(1,1);
        Integer match_count = response.getMatchCount();
        log.info("Match!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!{}",match_count);
        AptResponseDTO response2 = aptService.fetchAptData(1,match_count);
        if (response2 != null && response2.getData() != null) {
            for (AptDTO apt : response2.getData()) {
                log.info("Apt: {} \n", apt);
            }
        }
    }

    @Test
    void saveAptData() {
        AptResponseDTO response = aptService.fetchAptData(1,1);
        Integer match_count = response.getMatchCount();
        log.info("Match!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!{}",match_count);
        if (match_count > 1) {
            response = aptService.fetchAptData(1, match_count);
        }
        aptService.saveAptData(response);
        if (response != null && response.getData() != null) {
            for (AptDTO apt : response.getData()) {
                log.info("저장된 APT: {}", apt);
            }
        } else {
            fail("response 또는 data가 null입니다.");
        }
    }

    @Test
    void getAptTypeData() {
        AptTypeResponseDTO response = aptService.fetchAptTypeData("2025000326");
        log.info("전체 응답 {}",response);

        if (response != null && response.getData() != null) {
            for (AptTypeDTO apt : response.getData()) {
                log.info("Apt: {} \n", apt);
            }
        }

    }

    @Test
    void getAllAptTypeData() {
        AptTypeResponseDTO response = aptService.fetchAptTypeData("2024000694");
        Integer current_count = response.getCurrentCount();
        log.info("Current!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! : {}",current_count);
    }

    @Test
    void syncAptData() {
        aptService.syncAptData();
    }



}
