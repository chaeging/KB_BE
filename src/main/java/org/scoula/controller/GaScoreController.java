package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.dto.GaScoreDTO;
import org.scoula.service.GaScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@RestController
@RequestMapping("/v1/ga-score")
@RequiredArgsConstructor
public class GaScoreController {

    private final GaScoreService gaScoreService;

    @PostMapping
    public ResponseEntity<GaScoreDTO> saveGaScore(@RequestBody GaScoreDTO requestDto,
                                                  HttpServletRequest request) {
        log.info("POST /v1/ga-score request: {}", requestDto);

        GaScoreDTO responseDto = gaScoreService.saveGaScore(requestDto, request);

        return ResponseEntity.ok(responseDto);
    }
}
