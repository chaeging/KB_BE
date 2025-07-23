package org.scoula.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSelectedDTO {
    private HomePriceDTO homePrice;
    private List<RegionDTO> selectedRegion;
    private List<HomeSizeDTO> selectedHomesize;
    private List<HomeTypeDTO> selectedHometype;
}
