package org.scoula.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OfficetelResponseTypeDTO {
    private Integer matchCount;
    private List<OfficetelTypeDTO> data;
}
