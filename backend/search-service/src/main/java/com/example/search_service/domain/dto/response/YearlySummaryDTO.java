package com.example.search_service.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearlySummaryDTO {

    private Integer year;
    private Double totalValue;
}
