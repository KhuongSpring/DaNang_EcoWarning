package com.example.search_service.repository;

import com.example.search_service.domain.entity.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MetricRepository extends JpaRepository<Metric, UUID> {

    @Query("SELECT m.name, m.unit FROM Metric m WHERE m.category = :category")
    List<Object[]> findNameAndUnitByCategory(@Param("category") String category);
}
