package org.scoula.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficetelTypeDTO {
    private String modelNo;               // 모델번호
    private String tp;                    // 타입
    private BigDecimal excluseAr;        // 전용면적 (㎡)
    private Integer suplyHshldco;        // 공급세대수
    private Integer suplyAmount;         // 공급금액 (분양최고금액, 만원)
    private Integer subscrptReqstAmount; // 청약신청금 (만원)
}
