package org.scoula.dto;

import lombok.Data;

@Data
public class HouseListDTO {
    private String houseNm;              // 공고명
    private String hssplyAdres;          // 공급 주소
    private String applicationPeriod;    // 접수 기간 문자열 (예: 2025.07.31 ~ 2025.08.05)
    private Double suplyAr;              // 공급 면적 (apt_type.suply_ar 또는 officetel_type.excluse_ar)
    private Integer price;               // 분양가 (apt_type.lttot_top_amount 또는 officetel_type.suply_amount)
    private String houseType;            // 공고유형 (APT, 신혼희망타운, 오피스텔, 도시형생활주택)
}
