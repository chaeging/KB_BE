package org.scoula.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.dto.*;
import org.scoula.mapper.AptMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Log4j2
@PropertySource("classpath:/application.properties")
@RequiredArgsConstructor

public class OfficetelService {
    @Value("${house.decoding.key}")
    private String API_KEY;
    @Value("${office.url}")
    private String OFFICETEL_URL;
    @Value("${office.type.url}")
    private String OFFICETEL_TYPE_URL;

    private final AptMapper officeMapper;

    public OfficetelResponseDTO fetchOfficetelData(int page, int perPage) {
        try {
            // 오늘 날짜 월로 쿼리 날리기 yyyy-MM 포맷
            String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));

            StringBuilder urlBuilder = new StringBuilder(OFFICETEL_URL);
            urlBuilder.append("?")
                    .append("page=").append(page)
                    .append("&perPage=").append(perPage)
                    .append("&cond[RCRIT_PBLANC_DE::GTE]=").append(startDate)
                    .append("&serviceKey=").append(URLEncoder.encode(API_KEY, "UTF-8"));

            URL url = new URL(urlBuilder.toString());

            // 헤더 설정
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            log.info("Response Code: {}", responseCode);

            BufferedReader br = (responseCode >= 200 && responseCode <= 299)
                    ? new BufferedReader(new InputStreamReader(conn.getInputStream()))
                    : new BufferedReader(new InputStreamReader(conn.getErrorStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            conn.disconnect();
            log.info("응답 본문: {}", sb);

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.readValue(sb.toString(), OfficetelResponseDTO.class);

        } catch (Exception e) {
            log.error("오피스텔 데이터 요청 중 오류 발생", e);
            return null;
        }
    }

    public void saveOfficetelData(OfficetelResponseDTO responseDto) {
        if (responseDto != null && responseDto.getData() != null) {
            for (OfficetelDTO dto : responseDto.getData()) {
                if (dto.getMvnPrearngeYm() != null) {
                    LocalDate converted = LocalDate.parse(dto.getMvnPrearngeYm() + "01", DateTimeFormatter.ofPattern("yyyyMMdd"));
                    dto.setMvnPrearngeYm(converted.toString()); // "2025-08-01" 형식
                }

                officeMapper.insertOfficetel(dto);
            }
        }
    }

    public OfficetelTypeResponseDTO fetchOfficetelTypeData(String houseManageNo) {
        try {
            StringBuilder urlBuilder = new StringBuilder(OFFICETEL_TYPE_URL);
            urlBuilder.append("?page=1")
                    .append("&perPage=10000")
                    .append("&cond[HOUSE_MANAGE_NO::EQ]=").append(URLEncoder.encode(houseManageNo, "UTF-8"))
                    .append("&serviceKey=").append(URLEncoder.encode(API_KEY, "UTF-8"));

            URL url = new URL(urlBuilder.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            log.info("Response Code: {}", responseCode);

            BufferedReader br = (responseCode >= 200 && responseCode <= 299)
                    ? new BufferedReader(new InputStreamReader(conn.getInputStream()))
                    : new BufferedReader(new InputStreamReader(conn.getErrorStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            conn.disconnect();
            log.info("응답 본문: {}", sb);

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(sb.toString(), OfficetelTypeResponseDTO.class);

        } catch (Exception e) {
            log.error("아파트 타입 데이터 요청 중 오류 발생", e);
            return null;
        }
    }

    public List<OfficetelIdxDTO> getOfficetelIdxAndHouseManageNo() {
        List<OfficetelIdxDTO> aptList = officeMapper.getIdxAndHouseManageNoFromOfficetel();
        return aptList;
    }

    public void saveOfficetelTypes() {
        List<OfficetelIdxDTO> officetelList = getOfficetelIdxAndHouseManageNo();

        for (OfficetelIdxDTO dto : officetelList) {
            String houseManageNo = dto.getHouseManageNo();
            Integer officetelIdx = dto.getOfficetelIdx();

            // api 호출
            OfficetelTypeResponseDTO responseDTO = fetchOfficetelTypeData(houseManageNo);
            if (responseDTO == null || responseDTO.getData() == null) continue;

            // 2. 응답 결과 순회하며 apt_type 테이블 저장
            for (OfficetelTypeDTO typeDTO : responseDTO.getData()) {
                typeDTO.setOfficetelIdx(officetelIdx); // foreign key 설정
                officeMapper.insertOfficetelType(typeDTO);
            }
        }
    }

}
