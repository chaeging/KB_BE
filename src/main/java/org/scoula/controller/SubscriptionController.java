package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.dto.AptDetailDTO;
import org.scoula.dto.HouseListDTO;
import org.scoula.dto.OfficetelDetailDTO;
import org.scoula.service.AptService;
import org.scoula.service.HouseService;
import org.scoula.service.OfficetelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/v1/subscriptions")
public class SubscriptionController {
    private final HouseService houseService;
    private final AptService aptService;
    private final OfficetelService officetelService;

    @GetMapping("")
    public ResponseEntity<?> getHousingList() {
        List<HouseListDTO> list = houseService.getAllHousingList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/apartments/detail")
    public ResponseEntity<AptDetailDTO> getApartmentDetail(@RequestParam("pblanc_no") String pblancNo) {
        AptDetailDTO detail = aptService.getAptDetail(pblancNo);
        return ResponseEntity.ok(detail);
    }

    @GetMapping("/officetels/detail")
    public ResponseEntity<OfficetelDetailDTO> getOfficetelDetail(@RequestParam("pblanc_no") String pblancNo) {
        OfficetelDetailDTO detail = officetelService.getOfficetelDetail(pblancNo);
        return ResponseEntity.ok(detail);
    }
}
