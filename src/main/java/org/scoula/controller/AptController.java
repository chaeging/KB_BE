package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.dto.AptResponseDto;
import org.scoula.service.AptService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/apt")
@RequiredArgsConstructor
@Log4j2
public class AptController {

    private final AptService aptService;

    @GetMapping("/list")
    public AptResponseDto getAptList(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int perPage) {
        log.info("아파트 청약 목록 page: {}, perPage: {}", page, perPage);
        return aptService.fetchAptData(page, perPage);
    }
}
