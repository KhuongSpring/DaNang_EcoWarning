package com.example.collector_data_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObservationSearchDTO {

    private String id;
    private Instant timestamp;
    private Double value;

    private AssetInfo asset;
    private MetricInfo metric;

    @Data
    public static class AssetInfo {
        private String id;
        private String name;
        private String assetType;
    }

    @Data
    public static class MetricInfo {
        private String id;
        private String name;
        private String category;
        private String unit;
    }
}