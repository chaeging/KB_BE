package org.scoula.service;


import lombok.RequiredArgsConstructor;
import org.scoula.dto.ProbabilityDTO;
import org.scoula.mapper.ProbabilityMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProbabilityService {

    private final ProbabilityMapper probabilityMapper;

    public double calculateProbability(ProbabilityDTO input) {
        List<ScoreRecord> records = probabilityMapper.selectScoreRecords(
                input.getSido(), input.getSigungu(), input.getResidentCode());

        if (records.isEmpty()) return 0.0;

        long count = records.stream().filter(r -> r.getAvgScore() <= input.getScore()).count();
        double pHist = (double) count / records.size();

        records.sort(Comparator.comparingDouble(r -> distance(r, input)));
        int k = Math.min(5, records.size());
        List<ScoreRecord> knn = records.subList(0, k);

        double knnMin = knn.stream().mapToDouble(ScoreRecord::getMinScore).average().orElse(0);
        double knnMax = knn.stream().mapToDouble(ScoreRecord::getMaxScore).average().orElse(1);

        double pKnn = (input.getScore() - knnMin) / (knnMax - knnMin);
        pKnn = Math.max(0.0, Math.min(1.0, pKnn));

        double alpha = Math.min(1.0, records.size() / 20.0);
        return Math.round((alpha * pHist + (1 - alpha) * pKnn) * 10000.0) / 100.0;
    }

    public double calculateByAptIdx(int aptIdx, double score) {
        AptInfo apt = probabilityMapper.selectAptInfo(aptIdx);
        if (apt == null) return 0.0;

        ProbabilityDTO request = new ProbabilityDTO();
        request.setSido(apt.getSido());
        request.setSigungu(apt.getSigungu());
        request.setResidentCode(apt.getResidentCode());
        request.setSupplyCount(apt.getSupplyCount());
        request.setScore(score);

        return calculateProbability(request);
    }

    private double distance(ScoreRecord r, ProbabilityDTO input) {
        double d1 = Math.pow(r.getMinScore() - input.getScore(), 2);
        double d2 = Math.pow(r.getAvgScore() - input.getScore(), 2);
        double d3 = Math.pow(r.getMaxScore() - input.getScore(), 2);
        double d4 = Math.pow(r.getSupplyCount() - input.getSupplyCount(), 2);
        double d5 = r.getResidentCode() == input.getResidentCode() ? 0 : 100;
        return d1 + d2 + d3 + d4 + d5;
    }

    public static class ScoreRecord {
        private double minScore;
        private double avgScore;
        private double maxScore;
        private int supplyCount;
        private int residentCode;

        public double getMinScore() { return minScore; }
        public double getAvgScore() { return avgScore; }
        public double getMaxScore() { return maxScore; }
        public int getSupplyCount() { return supplyCount; }
        public int getResidentCode() { return residentCode; }

        public void setMinScore(double minScore) { this.minScore = minScore; }
        public void setAvgScore(double avgScore) { this.avgScore = avgScore; }
        public void setMaxScore(double maxScore) { this.maxScore = maxScore; }
        public void setSupplyCount(int supplyCount) { this.supplyCount = supplyCount; }
        public void setResidentCode(int residentCode) { this.residentCode = residentCode; }
    }

    public static class AptInfo {
        private String sido;
        private String sigungu;
        private int residentCode;
        private int supplyCount;

        public String getSido() { return sido; }
        public String getSigungu() { return sigungu; }
        public int getResidentCode() { return residentCode; }
        public int getSupplyCount() { return supplyCount; }

        public void setSido(String sido) { this.sido = sido; }
        public void setSigungu(String sigungu) { this.sigungu = sigungu; }
        public void setResidentCode(int residentCode) { this.residentCode = residentCode; }
        public void setSupplyCount(int supplyCount) { this.supplyCount = supplyCount; }
    }
}
