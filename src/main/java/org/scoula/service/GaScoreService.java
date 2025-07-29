package org.scoula.service;

import lombok.RequiredArgsConstructor;
import org.scoula.dto.GaScoreDTO;
import org.scoula.mapper.GaScoreMapper;
import org.scoula.mapper.UserMapper;
import org.scoula.security.util.JwtProcessor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GaScoreService {

    private final JwtProcessor jwtProcessor;
    private final UserMapper userMapper;
    private final GaScoreMapper gaScoreMapper;

    public void processAndSaveScore(String token, GaScoreDTO gaScoreDTO) {
        String userId = jwtProcessor.getUsername(token.replace("Bearer ", ""));
        int userIdx = userMapper.findUserIdxByUserId(userId);

        int noHouseScore = calculateNoHouseScore(gaScoreDTO.getNoHousePeriod());
        int dependentsScore = calculateDependentsScore(gaScoreDTO.getDependentsNm());
        int paymentPeriodScore = gaScoreDTO.getPaymentPeriodScore();

        int totalScore = noHouseScore + dependentsScore + paymentPeriodScore;

        gaScoreDTO.setNoHouseScore(noHouseScore);
        gaScoreDTO.setDependentsScore(dependentsScore);
        gaScoreDTO.setPaymentPeriodScore(paymentPeriodScore);

        gaScoreMapper.updateScore(userIdx, gaScoreDTO);
        gaScoreMapper.updateTotalScore(userIdx, totalScore);
    }

    public int calculateTotalScore(String token) {
        String userId = jwtProcessor.getUsername(token.replace("Bearer ", ""));
        int userIdx = userMapper.findUserIdxByUserId(userId);
        return gaScoreMapper.getTotalScoreByUserIdx(userIdx);
    }

    private int calculateNoHouseScore(int noHousePeriod) {
        return Math.min(noHousePeriod * 2, 20);
    }

    private int calculateDependentsScore(int dependentsNm) {
        return Math.min(dependentsNm * 5, 35);
    }
}
