package org.scoula.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.scoula.dto.HousingResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
public class HousingApiService {

    @Value("${external.api.serviceKey}")
    private String serviceKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String BASE_URL = "https://api.odcloud.kr/api/ApplyhomeInfoDetailSvc/v1";

    private String buildUrl(String endpoint, int page, int perPage) throws Exception {
        String encodedServiceKey = URLEncoder.encode(serviceKey, "UTF-8");
        return BASE_URL + endpoint
                + "?page=" + page
                + "&perPage=" + perPage
                + "&returnType=JSON"
                + "&serviceKey=" + encodedServiceKey;
    }

    private HousingResponseDTO callApi(String endpoint, int page, int perPage) {
        try {
            String url = buildUrl(endpoint, page, perPage);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return objectMapper.readValue(response.getBody(), HousingResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("API 호출 실패 (" + endpoint + "): " + e.getMessage(), e);
        }
    }

    public HousingResponseDTO getAptDetail(int page, int perPage) {
        return callApi("/getAPTltotPblancDetail", page, perPage);
    }

    public HousingResponseDTO getOfficetelDetail(int page, int perPage) {
        return callApi("/getUrbtyOfctlLttotPblancDetail", page, perPage);
    }

    public HousingResponseDTO getRemndrDetail(int page, int perPage) {
        return callApi("/getRemndrLttotPblancDetail", page, perPage);
    }

    public HousingResponseDTO getAptByHouseType(int page, int perPage) {
        return callApi("/getAPTltotPblancMdl", page, perPage);
    }

    public HousingResponseDTO getOfficetelByHouseType(int page, int perPage) {
        return callApi("/getUrbtyOfctlLttotPblancMdl", page, perPage);
    }

    public HousingResponseDTO getRemndrByHouseType(int page, int perPage) {
        return callApi("/getRemndrLttotPblancMdl", page, perPage);
    }
}
