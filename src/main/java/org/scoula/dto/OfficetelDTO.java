package org.scoula.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficetelDTO {

    private String houseManageNo;         // 주택관리번호
    private String pblancNo;              // 공고번호
    private String houseNm;               // 주택명
    private String houseSecd;             // 주택구분코드
    private String houseSecdNm;          // 주택구분코드명
    private String houseDtlSecd;         // 주택상세구분코드
    private String houseDtlSecdNm;       // 주택상세구분코드명
    private String searchHouseSecd;      // 주택구분 (상세코드)
    private String subscrptAreaCode;     // 공급지역코드
    private String subscrptAreaCodeNm;   // 공급지역명
    private String hssplyHshldco;        // 공급위치
    private Integer totSuplyHshldco;     // 공급규모
    private String rcritPblancDe;        // 모집공고일
    private String subscrptRceptBgnde;   // 청약접수시작일
    private String subscrptRceptEndde;   // 청약접수종료일
    private String przwnerPresnatnDe;    // 당첨자발표일
    private String cntrctCnclsBgnde;     // 계약시작일
    private String cntrctCnclsEndde;     // 계약종료일
    private String hmpgAdres;            // 홈페이지주소
    private String bsnsMbyNm;            // 사업주체명 (시행사)
    private String mdhsTelno;            // 문의처
    private String mvnPrearngeYm;        // 입주예정월 (YYYYMM)
    private String pblancUrl;            // 모집공고 상세 URL

    private List<OfficetelTypeDTO> officetelTypeList; // 연관된 모델 리스트
}

