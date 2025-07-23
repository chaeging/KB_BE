package org.scoula.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;

@Service
@RequiredArgsConstructor
public class HousingApiService {

    @Value("${external.api.serviceKey}")
    private String serviceKey;  // 인코딩 키

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String BASE_URL = "https://api.odcloud.kr/api/ApplyhomeInfoDetailSvc/v1";

    @PostConstruct
    public void init() {
        System.out.println("=== PostConstruct 실행됨 ===");
        System.out.println("serviceKey 값 확인: " + serviceKey);
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
            // URI 객체로 직접 전달 -> 인코딩 이슈 회피
            String fullUrl = BASE_URL + endpoint +
                    "?page=1&perPage=10&type=json" +
                    "&serviceKey=" + serviceKey;
            URI uri = new URI(fullUrl);  // 여기서 인코딩하지 않음

            System.out.println("=== [API 요청] ===");
            System.out.println("요청 URI: " + uri);
            System.out.println("=================");

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

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
}


