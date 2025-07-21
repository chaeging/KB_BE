package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import org.scoula.dto.HousingResponseDTO;
import org.scoula.service.HousingApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class HousingController {

    private final HousingApiService housingApiService;

    // 1️⃣ APT 분양정보 상세조회
    @GetMapping("/apt")
    public ResponseEntity<HousingResponseDTO> getAptDetail(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage) {
        return ResponseEntity.ok(housingApiService.getAptDetail(page, perPage));
    }

    // 2️⃣ 오피스텔/도시형/민간임대/생활숙박시설 분양정보 상세조회
    @GetMapping("/officetel")
    public ResponseEntity<HousingResponseDTO> getOfficetelDetail(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage) {
        return ResponseEntity.ok(housingApiService.getOfficetelDetail(page, perPage));
    }

    // 3️⃣ APT 잔여세대 분양정보 상세조회
    @GetMapping("/remndr")
    public ResponseEntity<HousingResponseDTO> getRemndrDetail(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage) {
        return ResponseEntity.ok(housingApiService.getRemndrDetail(page, perPage));
    }

    // 4️⃣ APT 분양정보 주택형별 상세조회
    @GetMapping("/apt-house-type")
    public ResponseEntity<HousingResponseDTO> getAptByHouseType(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage) {
        return ResponseEntity.ok(housingApiService.getAptByHouseType(page, perPage));
    }

    // 5️⃣ 오피스텔/도시형/민간임대/생활숙박시설 주택형별 상세조회
    @GetMapping("/officetel-house-type")
    public ResponseEntity<HousingResponseDTO> getOfficetelByHouseType(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage) {
        return ResponseEntity.ok(housingApiService.getOfficetelByHouseType(page, perPage));
    }

    // 6️⃣ APT 잔여세대 주택형별 상세조회
    @GetMapping("/remndr-house-type")
    public ResponseEntity<HousingResponseDTO> getRemndrByHouseType(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage) {
        return ResponseEntity.ok(housingApiService.getRemndrByHouseType(page, perPage));
    }
}
