package org.scoula.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AptDTO {

    private String houseManageNo;
    private String pblancNo;
    private String houseNm;
    private String houseSecd;
    private String houseSecdNm;
    private String houseDtlSecd;
    private String houseDtlSecdNm;
    private String rentSecd;
    private String rentSecdNm;
    private String subscrptAreaCode;
    private String subscrptAreaCodeNm;
    private String hssplyAdres;
    private Integer totSuplyHshldco;
    private String rcritPblancDe;
    private String rceptBgnde;
    private String rceptEndde;
    private String spsplyRceptBgnde;
    private String spsplyRceptEndde;
    private String gnrlRnk1CrspareaRcptde;
    private String gnrlRnk1CrspareaEndde;
    private String gnrlRnk1EtcAreaRcptde;
    private String gnrlRnk1EtcAreaEndde;
    private String gnrlRnk2CrspareaRcptde;
    private String gnrlRnk2CrspareaEndde;
    private String gnrlRnk2EtcAreaRcptde;
    private String gnrlRnk2EtcAreaEndde;
    private String przwnerPresnatnDe;
    private String cntrctCnclsBgnde;
    private String cntrctCnclsEndde;
    private String hmpgAdres;
    private String cnstrctEntrpsNm;
    private String mdhsTelno;
    private String bsnsMbyNm;
    private String mvnPrearngeYm;
    private String specltRdnEarthAt;
    private String mdatTrgetAreaSecd;
    private String parcprcUlsAt;
    private String pblancUrl;

    private List<AptTypeDTO> aptTypeList; // 연관된 주택형 리스트
}

