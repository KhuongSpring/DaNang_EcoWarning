package com.example.citizen_report_service.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequestDto {
    private String reportType;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private OffsetDateTime eventStartTime;

    private OffsetDateTime eventEndTime;

    private String addressText;

    private String reportDescription;

    private Object details;
}
