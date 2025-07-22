package org.scoula.controller;

import org.scoula.dto.GaScoreDTO;
import org.scoula.service.GaScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/ga-score")
public class GaScoreController {

    @Autowired
    private GaScoreService gaScoreService;

    @PostMapping("")
    public void saveGaScore(@RequestHeader("Authorization") String token,
                            @RequestBody GaScoreDTO gaScoreDTO) {
        gaScoreService.processAndSaveScore(token, gaScoreDTO);
    }

    @GetMapping("")
    public Map<String, Integer> getTotalScore(@RequestHeader("Authorization") String token) {
        int totalScore = gaScoreService.calculateTotalScore(token);

        Map<String, Integer> result = new HashMap<>();
        result.put("totalScore", totalScore);

        return result;
    }
}