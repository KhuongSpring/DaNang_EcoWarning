package com.example.search_service.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricYearlySummaryDTO {

    private String metricName;

    private Integer year;

    private Double totalValue;

    private String unit;
}