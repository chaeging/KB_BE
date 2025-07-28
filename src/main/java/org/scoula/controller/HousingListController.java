package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.dto.HouseListDTO;
import org.scoula.service.HouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/v1/subscriptions")
public class HousingListController {
    private final HouseService houseService;

    @GetMapping("")
    public ResponseEntity<?> getHousingList() {
        List<HouseListDTO> list = houseService.getAllHousingList();
        return ResponseEntity.ok(list);
    }
}
