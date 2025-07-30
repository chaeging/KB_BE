package org.scoula.service;

import lombok.extern.log4j.Log4j2;
import org.scoula.dto.AptDTO;
import org.scoula.dto.PlaceDTO;
import org.scoula.mapper.AptMapper;
import org.scoula.mapper.PlaceMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.*;

@Service
@Log4j2
public class KakaoMapInfraService {

    private static final String CATEGORY_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/category.json";

    private final AptMapper aptMapper;
    private final PlaceMapper placeMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${kakao.rest_key}")
    private String kakaoRestKey;

    public KakaoMapInfraService(AptMapper aptMapper, PlaceMapper placeMapper) {
        this.aptMapper = aptMapper;
        this.placeMapper = placeMapper;
    }

    /**
     * 모든 아파트에 대해 카테고리별 장소 검색 후 저장
     */
    public void fetchAndSavePlacesForAllApts() {
        List<AptDTO> aptList = aptMapper.findAllAptLocations();

        Map<String, String> categoryMap = Map.of(
                "HP8", "hospital",   // 병원
                "MT1", "mart",       // 대형마트
                "SC4", "school",     // 학교
                "SW8", "subway"      // 지하철역
        );

        for (AptDTO apt : aptList) {
            int aptIdx = apt.getAptIdx();
            long latitude = apt.getLatitude();
            long longitude = apt.getLongitude();

            for (Map.Entry<String, String> entry : categoryMap.entrySet()) {
                String categoryCode = entry.getKey();
                String placeType = entry.getValue();

                List<PlaceDTO> places = searchByCategory(latitude, longitude, categoryCode, 2000);

                for (PlaceDTO place : places) {
                    place.setAptIdx(aptIdx);
                    place.setOfficetelIdx(null); // 오피스텔 검색 시엔 이걸 세팅
                    place.setPlaceType(placeType);

                    switch (placeType) {
                        case "hospital":
                            placeMapper.insertHospital(place);
                            break;
                        case "school":
                            placeMapper.insertSchool(place);
                            break;
                        case "mart":
                            placeMapper.insertMart(place);
                            break;
                        case "subway":
                            placeMapper.insertSubway(place);
                            break;
                    }
                }

            }
        }
    }

    /**
     * 카테고리 장소 검색
     */
    private List<PlaceDTO> searchByCategory(double latitude, double longitude, String categoryCode, int radius) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(CATEGORY_SEARCH_URL)
                .queryParam("category_group_code", categoryCode)
                .queryParam("x", longitude)
                .queryParam("y", latitude)
                .queryParam("radius", radius)
                .queryParam("size", 10);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoRestKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map<String, Object> body = response.getBody();
        if (body == null || body.get("documents") == null) {
            return Collections.emptyList();
        }

        List<Map<String, Object>> documents = (List<Map<String, Object>>) body.get("documents");
        List<PlaceDTO> results = new ArrayList<>();

        for (Map<String, Object> doc : documents) {
            PlaceDTO dto = new PlaceDTO();
            dto.setPlaceName((String) doc.get("place_name"));

            // 도로명 주소가 없으면 지번 주소 사용
            String roadAddress = (String) doc.get("road_address_name");
            String jibunAddress = (String) doc.get("address_name");
            dto.setAddress(roadAddress != null && !roadAddress.isEmpty() ? roadAddress : jibunAddress);

            dto.setLatitude(Double.parseDouble((String) doc.get("y")));
            dto.setLongitude(Double.parseDouble((String) doc.get("x")));
            results.add(dto);
        }

        return results;
    }
}
