package org.scoula.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class HousingApiService {

    @Value("${external.api.serviceKey}")
    private String serviceKey;  // serviceKey (인코딩키 or 디코딩키) → query param

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
            // ✅ 요청 URL 만들기
            String url = BASE_URL + endpoint
                    + "?page=1&perPage=10&type=json"
                    + "&serviceKey=" + serviceKey;

            // ✅ 요청 정보 로그 출력
            System.out.println("=== [API 요청 정보] ===");
            System.out.println("요청 URL: " + url);

            System.out.println("=====================");


            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "PostmanRuntime/7.44.1");  // 브라우저 흉내

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            // ✅ JSON 파싱 후 리턴
            return objectMapper.readTree(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("API 호출 실패: " + endpoint + " - " + e.getMessage(), e);
        }
    }

    // 테스트용 setter (Spring 없이 main에서 쓸 때)
    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }
}
