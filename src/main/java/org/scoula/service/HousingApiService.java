package org.scoula.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class HousingApiService {

    @Value("${external.api.serviceKey}")
    private String serviceKey;  // 인코딩키

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String BASE_URL = "https://api.odcloud.kr/api/ApplyhomeInfoDetailSvc/v1";

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
            // ✅ 요청 URL 구성
            String url = BASE_URL + endpoint +
                    "?page=1&perPage=10&type=json" +
                    "&serviceKey=" + serviceKey;

            // ✅ 요청 헤더 구성 (User-Agent 필수 아님)
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            System.out.println("=== [API 요청] ===");
            System.out.println("요청 URL: " + url);
            System.out.println("=================");

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            // ✅ 응답 확인
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                System.out.println("✅ 응답 성공: " + response.getStatusCode());
                return objectMapper.readTree(response.getBody());
            } else {
                System.err.println("❌ 응답 실패: " + response.getStatusCode());
                throw new RuntimeException("API 응답 오류: " + response.getStatusCode());
            }

        } catch (Exception e) {
            System.err.println("❌ 예외 발생: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("API 호출 실패: " + endpoint + " - " + e.getMessage(), e);
        }
    }

//    // 테스트용 setter
//    public void setServiceKey(String serviceKey) {
//        this.serviceKey = serviceKey;
//    }
}
