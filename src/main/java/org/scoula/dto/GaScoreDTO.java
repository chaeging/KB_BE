package org.scoula.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GaScoreDTO {

    @JsonProperty("no_house_period")
    private int noHousePeriod;
    @JsonProperty("dependents_nm")
    private int dependentsNm;
    private int noHouseScore;
    private int dependentsScore;
    private int paymentPeriod;
}
