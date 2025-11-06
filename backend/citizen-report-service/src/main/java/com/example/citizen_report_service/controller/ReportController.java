package com.example.citizen_report_service.controller;

import com.example.citizen_report_service.base.RestApiV1;
import com.example.citizen_report_service.base.VsResponseUtil;
import com.example.citizen_report_service.constant.UrlConstant;
import com.example.citizen_report_service.domain.dto.request.ReportRequestDto;
import com.example.citizen_report_service.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@RestApiV1
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @Operation(
            summary = "Báo cáo sự cố",
            description = "Dùng để báo cáo các sự cố xảy ra"
    )
    @PostMapping(UrlConstant.Report.SEND_REPORT)
    public ResponseEntity<?> getMapAssets(
            @RequestPart("request") @Validated ReportRequestDto request,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) {
        return VsResponseUtil.success(reportService.createReport(request, imageFile));
    }
}
