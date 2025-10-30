package com.example.search_service.domain.dto.response;

import com.example.search_service.domain.entity.Observation;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeSeriesDataPoint {
    private LocalDateTime timestamp;
    private Double value;

    public TimeSeriesDataPoint(Observation obs) {
        this.timestamp = obs.getRecordTime();
        this.value = obs.getValue();
    }
}
