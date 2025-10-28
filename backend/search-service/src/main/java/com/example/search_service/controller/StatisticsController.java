package com.example.search_service.controller;

import com.example.search_service.base.RestApiV1;
import com.example.search_service.base.VsResponseUtil;
import com.example.search_service.constant.UrlConstant;
import com.example.search_service.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestApiV1
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Operation(
            summary = "Đếm số asset",
            description = "Đếm số asset hiện có"
    )
    @GetMapping(UrlConstant.Static.COUNT_TYPE)
    public ResponseEntity<?> getAssetCountsByType() {
        return VsResponseUtil.success(statisticsService.getAssetCountsByType());
    }

    @Operation(
            summary = "Lấy thông tin về thiệt hại do thiên tai qua từng năm",
            description = "Lấy thông tin về thiệt hại do thiên tai qua từng năm (Tỷ đồng)"
    )
    @GetMapping(UrlConstant.Static.DISASTER_DAMAGE_BY_YEAR)
    public ResponseEntity<?> getDisasterDamageSummary() {
        return VsResponseUtil.success(statisticsService.getDisasterDamageSummaryByYear());
    }

    @Operation(
            summary = "Lấy thông tin về thiệt hại do thiên tai trong một năm",
            description = "Lấy thông tin về thiệt hại do thiên tai trong một năm"
    )
    @GetMapping(UrlConstant.Static.DISASTER_DAMAGE_DETAIL)
    public ResponseEntity<?> getDisasterDamageDetails(
            @RequestParam Integer year
    ) {
        return VsResponseUtil.success(statisticsService.getDisasterDamageDetailsByYear(year));
    }

    @Operation(
            summary = "Lấy thông tin chi tiết về nông nghiệp theo đơn vị tính",
            description = "Lấy thông tin chi tiết về nông nghiệp theo đơn vị tính"
    )
    @GetMapping(UrlConstant.Static.AGRICULTURE_SUMMARY_BY_YEAR)
    public ResponseEntity<?> getAgricultureSummary(
            @RequestParam(defaultValue = "ĐVT: Ha / Ha / Tạ/Ha / Tấn") String unit
    ) {
        return VsResponseUtil.success(statisticsService.getAgricultureSummaryByUnit(unit));
    }
}
