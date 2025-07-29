package org.scoula.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GaScoreDTO {

    @JsonProperty("no_house_period")
    private int noHousePeriod;

    @JsonProperty("dependents_nm")
    private int dependentsNm;

    @JsonProperty("no_house_score")
    private int noHouseScore;

    @JsonProperty("dependents_score")
    private int dependentsScore;

    @JsonProperty("payment_period")
    private int paymentPeriod;

    @JsonProperty("payment_period_score")
    private int paymentPeriodScore;

    @JsonProperty("head_of_household")
    private int headOfHousehold;

    @JsonProperty("house_owner")
    private int houseOwner;

    @JsonProperty("house_disposal")
    private int houseDisposal;

    @JsonProperty("disposal_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate disposalDate;

    @JsonProperty("marital_status")
    private int maritalStatus;

    @JsonProperty("wedding_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate weddingDate;

    @JsonProperty("total_ga_score")
    private int totalGaScore;

}
