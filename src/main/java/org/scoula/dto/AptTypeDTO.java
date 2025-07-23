package org.scoula.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AptTypeDTO {
    private Integer aptTypeIdx;      // APT 타입 고유 ID
    private String houseTy;          // 주택형
    private BigDecimal suplyAr;      // 공급면적
    private Integer suplyHshldco;    // 일반공급세대수
    private Integer spsplyHshldco;   // 특별공급세대수
    private String lttotTopAmount;   // 공급금액 (분양최고금액)
}

