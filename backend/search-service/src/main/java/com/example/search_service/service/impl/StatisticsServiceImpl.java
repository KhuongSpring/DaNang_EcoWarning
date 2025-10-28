package com.example.search_service.service.impl;

import com.example.search_service.domain.dto.MetricValueDTO;
import com.example.search_service.domain.dto.response.AssetTypeCountDTO;
import com.example.search_service.domain.dto.response.MetricYearlySummaryDTO;
import com.example.search_service.domain.dto.response.YearlySummaryDTO;
import com.example.search_service.repository.AssetSearchRepository;
import com.example.search_service.repository.ObservationSearchRepository;
import com.example.search_service.service.StatisticsService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsServiceImpl implements StatisticsService {
    private final AssetSearchRepository assetRepository;
    private final ObservationSearchRepository observationRepository;

    private static final String CATEGORY_DISASTER_DAMAGE = "Thiệt hại thiên tai";
    private static final String UNIT_MONEY = "Tỷ đồng";

    private static final String CATEGORY_AGRICULTURE = "Nông nghiệp";

    @Override
    public List<AssetTypeCountDTO> getAssetCountsByType() {
        return assetRepository.countAssetsByType();
    }

    @Override
    public List<YearlySummaryDTO> getDisasterDamageSummaryByYear() {
        return observationRepository.getYearlySummaryByCategoryAndUnit(
                CATEGORY_DISASTER_DAMAGE,
                UNIT_MONEY
        );
    }

    @Override
    public List<MetricValueDTO> getDisasterDamageDetailsByYear(Integer year) {
        return observationRepository.getDetailedSummaryByCategoryAndYear(
                CATEGORY_DISASTER_DAMAGE,
                year
        );
    }

    public List<MetricYearlySummaryDTO> getAgricultureSummaryByUnit(String unit) {
        return observationRepository.getYearlySummaryByMetricAndYear(
                CATEGORY_AGRICULTURE,
                unit
        );
    }
}
