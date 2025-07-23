package org.scoula.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.scoula.service.HousingApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

@RestController
@RequestMapping("/api/v1/db")
@RequiredArgsConstructor
public class HousingController {

    private final HousingApiService housingApiService;
    private final ObjectMapper objectMapper;

    // 공통 예외 처리 메서드
    private ResponseEntity<?> handleServiceCall(Supplier<JsonNode> serviceCall, String failMessage) {
        try {
            return ResponseEntity.ok(serviceCall.get());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(failMessage + ": " + e.getMessage());
        }
    }

    // ✅ [통합 조회] 전체 청약 정보 조회
    @GetMapping("/all-housing")
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

    // 1️⃣ APT 분양정보 상세조회
    @GetMapping("/apt")
    public ResponseEntity<?> getAptDetail() {
        return handleServiceCall(housingApiService::getAptDetail, "APT 상세조회 실패");
    }

    // 2️⃣ 오피스텔/도시형/민간임대/생활숙박시설 분양정보 상세조회
    @GetMapping("/officetel")
    public ResponseEntity<?> getOfficetelDetail() {
        return handleServiceCall(housingApiService::getOfficetelDetail, "오피스텔 상세조회 실패");
    }

    // 3️⃣ APT 잔여세대 분양정보 상세조회
    @GetMapping("/remndr")
    public ResponseEntity<?> getRemndrDetail() {
        return handleServiceCall(housingApiService::getRemndrDetail, "잔여세대 상세조회 실패");
    }

    // 4️⃣ APT 분양정보 주택형별 상세조회
    @GetMapping("/apt-type")
    public ResponseEntity<?> getAptByHouseType() {
        return handleServiceCall(housingApiService::getAptByHouseType, "APT 주택형별 상세조회 실패");
    }

    // 5️⃣ 오피스텔/도시형/민간임대/생활숙박시설 주택형별 상세조회
    @GetMapping("/officetel-type")
    public ResponseEntity<?> getOfficetelByHouseType() {
        return handleServiceCall(housingApiService::getOfficetelByHouseType, "오피스텔 주택형별 상세조회 실패");
    }

    // 6️⃣ APT 잔여세대 주택형별 상세조회
    @GetMapping("/remndr-type")
    public ResponseEntity<?> getRemndrByHouseType() {
        return handleServiceCall(housingApiService::getRemndrByHouseType, "잔여세대 주택형별 상세조회 실패");
    }
}
