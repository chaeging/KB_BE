package org.scoula.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class HousingApiServiceTest {

    public static void main(String[] args) {
        // RestTemplate, ObjectMapper 직접 생성
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        // HousingApiService 생성자 주입
        HousingApiService housingApiService = new HousingApiService(restTemplate, objectMapper);

        // Spring 없이 serviceKey 직접 세팅
        housingApiService.setServiceKey("PQGpmr1zaI3HKxa%2BckRamEynSodJqI7OQ%2BhFp25TeBWQ81tmjkuGif%2F3XyZVhGcZxf2%2Fk3wB8ERkC8iYqQUkcA%3D%3D");

        try {
            callAndPrint("APT Detail", housingApiService.getAptDetail());
            callAndPrint("Officetel Detail", housingApiService.getOfficetelDetail());
            callAndPrint("Remndr Detail", housingApiService.getRemndrDetail());
            callAndPrint("APT By HouseType", housingApiService.getAptByHouseType());
            callAndPrint("Officetel By HouseType", housingApiService.getOfficetelByHouseType());
            callAndPrint("Remndr By HouseType", housingApiService.getRemndrByHouseType());
        } catch (Exception e) {
            System.err.println("테스트 중 에러 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void callAndPrint(String label, JsonNode response) {
        System.out.println("=== " + label + " ===");
        System.out.println(response.toPrettyString());
        System.out.println();
    }
}
