package com.example.search_service.util;

import com.example.search_service.domain.dto.ObservationSearchCriteriaDto;
import com.example.search_service.domain.entity.Asset;
import com.example.search_service.domain.entity.Metric;
import com.example.search_service.domain.entity.Observation;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ObservationSpecification {

    public Specification<Observation> build(ObservationSearchCriteriaDto criteria) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            Join<Observation, Asset> assetJoin = root.join("asset", JoinType.LEFT);
            Join<Observation, Metric> metricJoin = root.join("metric", JoinType.LEFT);

            if (criteria.getQ() != null && !criteria.getQ().isEmpty()) {
                String likePattern = "%" + criteria.getQ().toLowerCase() + "%";

                predicates.add(cb.or(
                        cb.like(cb.lower(assetJoin.get("name")), likePattern),
                        cb.like(cb.lower(metricJoin.get("name")), likePattern)
                ));
            }

            if (criteria.getAssetId() != null) {
                predicates.add(cb.equal(assetJoin.get("id"), criteria.getAssetId()));
            }
            if (criteria.getAssetType() != null) {
                predicates.add(cb.equal(assetJoin.get("assetType"), criteria.getAssetType()));
            }
            if (criteria.getDistrict() != null) {
                predicates.add(cb.equal(assetJoin.get("district"), criteria.getDistrict()));
            }

            if (criteria.getMetricId() != null) {
                predicates.add(cb.equal(metricJoin.get("id"), criteria.getMetricId()));
            }
            if (criteria.getMetricCategory() != null) {
                predicates.add(cb.equal(metricJoin.get("category"), criteria.getMetricCategory()));
            }

            if (criteria.getFromDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("recordTime"), criteria.getFromDate().atStartOfDay()));
            }
            if (criteria.getToDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("recordTime"), criteria.getToDate().atTime(LocalTime.MAX)));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
