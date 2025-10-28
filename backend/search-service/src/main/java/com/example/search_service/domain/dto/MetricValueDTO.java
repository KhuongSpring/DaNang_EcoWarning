package com.example.search_service.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricValueDTO {

    private String metricName;
    private Double value;
    private String unit;
}