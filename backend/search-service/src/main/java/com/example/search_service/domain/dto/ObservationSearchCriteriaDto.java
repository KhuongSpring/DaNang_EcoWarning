package com.example.search_service.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObservationSearchCriteriaDto {

    private String q;

    private String assetId;
    private String assetType;
    private String district;

    private String metricId;
    private String metricCategory;

    private LocalDate fromDate;
    private LocalDate toDate;
}