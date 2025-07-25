package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import org.scoula.dto.GaScoreDTO;
import org.scoula.service.GaScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/ga-score")
@RequiredArgsConstructor
public class GaScoreController {

    private final GaScoreService gaScoreService;

    @PostMapping("")
    public ResponseEntity<Map<String, String>> saveGaScore(@RequestHeader("Authorization") String token,
                                                           @RequestBody GaScoreDTO gaScoreDTO) {
        gaScoreService.processAndSaveScore(token, gaScoreDTO);

        Map<String, String> response = new HashMap<>();
        response.put("message", "가점이 저장되었습니다.");

        return ResponseEntity.ok(response);
    }


    @GetMapping("")
    public Map<String, Integer> getTotalScore(@RequestHeader("Authorization") String token) {
        int totalScore = gaScoreService.calculateTotalScore(token);

        Map<String, Integer> result = new HashMap<>();
        result.put("total_ga_score", totalScore);  // 키 이름을 total_ga_score로 맞춤

        return result;
    }
}
