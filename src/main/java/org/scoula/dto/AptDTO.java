package org.scoula.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AptDTO {

    private String houseManageNo;         // 주택관리번호
    private String pblancNo;              // 공고번호
    private String houseNm;               // 주택명
    private String houseSecd;             // 주택구분코드
    private String houseSecdNm;           // 주택구분코드명
    private String houseDtlSecd;          // 주택상세구분코드
    private String houseDtlSecdNm;        // 주택상세구분코드명
    private String rentSecd;              // 분양구분코드
    private String rentSecdNm;            // 분양구분코드명
    private String subscrptAreaCode;      // 공급지역코드
    private String subscrptAreaCodeNm;    // 공급지역명
    private String hssplyAdres;           // 공급위치
    private Integer totSuplyHshldco;      // 공급규모
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate rcritPblancDe;      // 모집공고일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate rceptBgnde;         // 청약접수시작일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate rceptEndde;         // 청약접수종료일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate spsplyRceptBgnde;   // 특별공급 접수시작일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate spsplyRceptEndde;   // 특별공급 접수종료일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate gnrlRnk1CrspareaRcptde; // 1순위 해당지역 접수시작일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate gnrlRnk1CrspareaEndde;  // 1순위 해당지역 접수종료일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate gnrlRnk1EtcAreaRcptde;  // 1순위 기타지역 접수시작일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate gnrlRnk1EtcAreaEndde;   // 1순위 기타지역 접수종료일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate gnrlRnk2CrspareaRcptde; // 2순위 해당지역 접수시작일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate gnrlRnk2CrspareaEndde;  // 2순위 해당지역 접수종료일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate gnrlRnk2EtcAreaRcptde;  // 2순위 기타지역 접수시작일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate gnrlRnk2EtcAreaEndde;   // 2순위 기타지역 접수종료일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate przwnerPresnatnDe;      // 당첨자 발표일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate cntrctCnclsBgnde;       // 계약 시작일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate cntrctCnclsEndde;       // 계약 종료일
    private String hmpgAdres;                 // 홈페이지 주소
    private String cnstrctEntrpsNm;           // 건설업체명 (시공사)
    private String mdhsTelno;                 // 문의처
    private String bsnsMbyNm;                 // 사업주체명 (시행사)
    private String mvnPrearngeYm;             // 입주 예정월 (YYYYMM)
    private String specltRdnEarthAt;          // 투기과열지구 여부 (Y/N)
    private String mdatTrgetAreaSecd;         // 조정대상지역 여부 (Y/N)
    private String parcprcUlsAt;              // 분양가상한제 적용 여부 (Y/N)
    private String pblancUrl;                 // 모집공고 상세 URL
}

