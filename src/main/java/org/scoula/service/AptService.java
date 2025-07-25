package org.scoula.service;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.scoula.dto.AptDTO;
import org.scoula.dto.AptResponseDto;
import org.scoula.mapper.AptMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

@Service
@Log4j2
@PropertySource("classpath:/application.properties")
@RequiredArgsConstructor
public class AptService {

    @Value("${house.decoding.key}")
    private String API_KEY;
    @Value("${house.url}")
    private String HOUSE_URL;
    private final AptMapper aptMapper;

    public AptResponseDto fetchAptData(int page, int perPage,String startDate ) {
        try {

            //URL만들기
            StringBuilder urlBuilder = new StringBuilder(HOUSE_URL);
            urlBuilder.append("?")
                    .append("page=").append(page)
                    .append("&perPage=").append(perPage)
                    .append("&cond[RCRIT_PBLANC_DE::GTE]=").append(startDate)
                    .append("&serviceKey=").append(URLEncoder.encode(API_KEY, "UTF-8"));

            URL url = new URL(urlBuilder.toString());

            //헤더 설정
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            log.info("Response Code: {}", responseCode);

            BufferedReader br;
            if (responseCode >= 200 && responseCode <= 299) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            conn.disconnect();
            log.info("응답 본문: {}", sb);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); //LocalDate
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.readValue(sb.toString(), AptResponseDto.class);

        } catch (Exception e) {
            log.error("아파트 데이터 요청 중 오류 발생", e);
            return null;
        }
    }

    public void saveAptData(AptResponseDto responseDto) {
        if (responseDto != null && responseDto.getData() != null) {
            for (AptDTO dto : responseDto.getData()) {
                aptMapper.insertApt(dto);
            }
        }
    }
}
