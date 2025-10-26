package com.example.collector_data_service.domain.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DomainEventDTO<T> {

    UUID eventId;
    String eventType;
    Instant eventTimestamp;
    String sourceService;
    T payload;

    public DomainEventDTO(String eventType, String sourceService, T payload) {
        this.eventId = UUID.randomUUID();
        this.eventTimestamp = Instant.now();
        this.eventType = eventType;
        this.sourceService = sourceService;
        this.payload = payload;
    }
}