package org.scoula.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.scoula.service.HousingApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class HousingController {

    private final HousingApiService housingApiService;
    private final ObjectMapper objectMapper;

    // 통합 호출 API
    @GetMapping
    public ResponseEntity<?> getAllSubscriptions() {
        try {
            ObjectNode result = objectMapper.createObjectNode();
            result.set("aptDetail", housingApiService.getAptDetail());
            result.set("officetelDetail", housingApiService.getOfficetelDetail());
            result.set("remndrDetail", housingApiService.getRemndrDetail());
            result.set("aptByHouseType", housingApiService.getAptByHouseType());
            result.set("officetelByHouseType", housingApiService.getOfficetelByHouseType());
            result.set("remndrByHouseType", housingApiService.getRemndrByHouseType());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("통합 청약 정보 조회 실패: " + e.getMessage());
        }
    }

    // 1 APT 분양정보 상세조회
    @GetMapping("/apt")
    public ResponseEntity<?> getAptDetail() {
        try {
            return ResponseEntity.ok(housingApiService.getAptDetail());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("APT 상세조회 실패: " + e.getMessage());
        }
    }

    // 2 오피스텔/도시형/민간임대/생활숙박시설 분양정보 상세조회
    @GetMapping("/officetel")
    public ResponseEntity<?> getOfficetelDetail() {
        try {
            return ResponseEntity.ok(housingApiService.getOfficetelDetail());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("오피스텔 상세조회 실패: " + e.getMessage());
        }
    }

    // 3 APT 잔여세대 분양정보 상세조회
    @GetMapping("/remndr")
    public ResponseEntity<?> getRemndrDetail() {
        try {
            return ResponseEntity.ok(housingApiService.getRemndrDetail());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("잔여세대 상세조회 실패: " + e.getMessage());
        }
    }

    // 4 APT 분양정보 주택형별 상세조회
    @GetMapping("/apt-type")
    public ResponseEntity<?> getAptByHouseType() {
        try {
            return ResponseEntity.ok(housingApiService.getAptByHouseType());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("APT 주택형별 상세조회 실패: " + e.getMessage());
        }
    }

    // 5 오피스텔/도시형/민간임대/생활숙박시설 주택형별 상세조회
    @GetMapping("/officetel-type")
    public ResponseEntity<?> getOfficetelByHouseType() {
        try {
            return ResponseEntity.ok(housingApiService.getOfficetelByHouseType());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("오피스텔 주택형별 상세조회 실패: " + e.getMessage());
        }
    }

    // 6 APT 잔여세대 주택형별 상세조회
    @GetMapping("/remndr-type")
    public ResponseEntity<?> getRemndrByHouseType() {
        try {
            return ResponseEntity.ok(housingApiService.getRemndrByHouseType());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("잔여세대 주택형별 상세조회 실패: " + e.getMessage());
        }
    }
}
