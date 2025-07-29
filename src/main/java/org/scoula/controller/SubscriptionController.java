package org.scoula.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(tags = "청약공고 API", description = "모든 청약공고 조회 및 상세 조회 기능 제공")
@RequestMapping("/v1/subscriptions")
public class SubscriptionController {
    private final HouseService houseService;
    private final AptService aptService;
    private final OfficetelService officetelService;

    @GetMapping("")
    @ApiOperation(
            value = "모든 청약공고 가져오기",
            notes = "page, pageSize 쿼리 파라미터로 페이징 처리합니다. 인증 헤더 필요"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "성공",
                    response = HouseListDTO.class,
                    responseContainer = "List"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getHousingList() {
        List<HouseListDTO> list = houseService.getAllHousingList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/apartments/detail")
    @ApiOperation(
            value = "아파트 청약공고 상세 정보 조회",
            notes = "pblanc_no(청약공고번호)로 아파트 상세 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = AptDetailDTO.class),
            @ApiResponse(code = 404, message = "공고를 찾을 수 없음"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<AptDetailDTO> getApartmentDetail(@RequestParam("pblanc_no") String pblancNo) {
        AptDetailDTO detail = aptService.getAptDetail(pblancNo);
        return ResponseEntity.ok(detail);
    }

    @GetMapping("/officetels/detail")
    @ApiOperation(
            value = "오피스텔 청약공고 상세 정보 조회",
            notes = "pblanc_no(청약공고번호)로 오피스텔 상세 정보를 조회합니다."
    ) @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = OfficetelDetailDTO.class),
            @ApiResponse(code = 404, message = "공고를 찾을 수 없음"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<OfficetelDetailDTO> getOfficetelDetail(@RequestParam("pblanc_no") String pblancNo) {
        OfficetelDetailDTO detail = officetelService.getOfficetelDetail(pblancNo);
        return ResponseEntity.ok(detail);
    }
}
