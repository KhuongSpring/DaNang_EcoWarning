package com.example.collector_data_service.service;

import com.example.collector_data_service.domain.dto.DomainEventDTO;
import com.example.collector_data_service.domain.dto.ObservationSearchDTO;
import com.example.collector_data_service.domain.entity.Asset;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KafkaProducerService {

    final KafkaTemplate<String, DomainEventDTO<?>> kafkaTemplate;
    @Value("${spring.application.name}")
    static String SOURCE_SERVICE_NAME;

    public KafkaProducerService(KafkaTemplate<String, DomainEventDTO<?>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAssetEvent(String topic, String eventType, Asset asset) {
        DomainEventDTO<Asset> event = new DomainEventDTO<>(
                eventType,
                SOURCE_SERVICE_NAME,
                asset
        );

        kafkaTemplate.send(topic, asset.getId().toString(), event);
    }

    public void sendObservationEvent(String topic, String eventType, ObservationSearchDTO observationSearchDTO) {
        DomainEventDTO<ObservationSearchDTO> event = new DomainEventDTO<>(
                eventType,
                SOURCE_SERVICE_NAME,
                observationSearchDTO
        );

        kafkaTemplate.send(topic, observationSearchDTO.getId(), event);
    }
}