package com.example.search_service.service;

import com.example.search_service.util.ObservationSpecification;
import com.example.search_service.domain.dto.ObservationSearchCriteriaDto;
import com.example.search_service.domain.entity.Observation;
import com.example.search_service.repository.ObservationSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final ObservationSearchRepository observationRepository;
    private final ObservationSpecification observationSpecification;

    public Page<Observation> searchObservations(ObservationSearchCriteriaDto criteria, Pageable pageable) {

        Specification<Observation> spec = observationSpecification.build(criteria);

        Page<Observation> results = observationRepository.findAll(spec, pageable);

        return results;
    }
}
