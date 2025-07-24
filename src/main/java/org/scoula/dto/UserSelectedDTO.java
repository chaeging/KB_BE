package org.scoula.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSelectedDTO {

    @JsonProperty("userInfo")
    private HomePriceDTO homePrice = new HomePriceDTO();
    private List<RegionDTO> selectedRegion = new ArrayList<>();
    private List<HomeSizeDTO> selectedHomeSize = new ArrayList<>();
    private List<HomeTypeDTO> selectedHomeType = new ArrayList<>();
}
