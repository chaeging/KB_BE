package org.scoula.dto;

import lombok.Data;

import java.util.List;

@Data
public class HousingResponseDTO {
    private int page;
    private int perPage;
    private int totalCount;
    private List<Item> data;

    @Data
    public static class Item {
        private String HOUSE_MANAGE_NO;
        private String PBLANC_NO;
        private String HOUSE_NM;
        private String HOUSE_SECD;
        private String HOUSE_DTL_SECD;
        private String SUBSCRPT_AREA_CODE;
        private String RCRIT_PBLANC_DE;
    }
}