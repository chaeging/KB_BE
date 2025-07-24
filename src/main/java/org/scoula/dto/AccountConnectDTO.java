package org.scoula.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountConnectDTO {
    private String id;
    private String organization;

    @JsonProperty("bank_name")
    private String bankName;

    private String password;
}
