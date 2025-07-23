package org.scoula.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class HousingApiService {

    @Value("${external.api.serviceKey}")
    private String serviceKey;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String BASE_URL = "https://api.odcloud.kr/api/ApplyhomeInfoDetailSvc/v1";

    @PostConstruct
    public void init() {
        System.out.println("=== PostConstruct 실행됨 ===");
        System.out.println("serviceKey 값: " + serviceKey);
    }

    public JsonNode getAptDetail() {
        return callApi("/getAPTLttotPblancDetail");
    }

    public JsonNode getOfficetelDetail() {
        return callApi("/getUrbtyOfctlLttotPblancDetail");
    }

    public JsonNode getRemndrDetail() {
        return callApi("/getRemndrLttotPblancDetail");
    }

    public JsonNode getAptByHouseType() {
        return callApi("/getAPTLttotPblancMdl");
    }

    public JsonNode getOfficetelByHouseType() {
        return callApi("/getUrbtyOfctlLttotPblancMdl");
    }

    public JsonNode getRemndrByHouseType() {
        return callApi("/getRemndrLttotPblancMdl");
    }
    private JsonNode callApi(String endpoint) {
        try {
            String url = BASE_URL + endpoint +
                    "?page=1&perPage=10&type=json" +
                    "&serviceKey=" + serviceKey;

            System.out.println("=== API 요청 URL ===");
            System.out.println(url);
            System.out.println("===================");

            // ✅ RestTemplate 간단 호출
            String result = restTemplate.getForObject(url, String.class);

            System.out.println("✅ 응답 본문:");
            System.out.println(result);

            return objectMapper.readTree(result);

        } catch (Exception e) {
            System.err.println("❌ 예외 발생: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("API 호출 실패: " + endpoint + " - " + e.getMessage(), e);
        }
    }


//    private JsonNode callApi(String endpoint) {
//        try {
//            String url = BASE_URL + endpoint +
//                    "?page=1&perPage=10&type=json" +
//                    "&serviceKey=" + serviceKey;
//
//            HttpHeaders headers = new HttpHeaders();
////            headers.set("Accept", "application/json");
//
//            HttpEntity<String> entity = new HttpEntity<>(headers);
//
//            System.out.println(" === API 요청 URL: " + url);
//
//            ResponseEntity<String> response = restTemplate.exchange(
//                    url,
//                    HttpMethod.GET,
//                    entity,
//                    String.class
//            );
//
//            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//                System.out.println("✅ 응답 성공: " + response.getStatusCode());
//                return objectMapper.readTree(response.getBody());
//            } else {
//                System.err.println("❌ 응답 실패: " + response.getStatusCode());
//                throw new RuntimeException("API 응답 오류: " + response.getStatusCode());
//            }
//
//        } catch (Exception e) {
//            System.err.println("❌ 예외 발생: " + e.getMessage());
//            e.printStackTrace();
//            throw new RuntimeException("API 호출 실패: " + endpoint + " - " + e.getMessage(), e);
//        }
//    }
}
