package com.example.collector_data_service.repository;

import com.example.collector_data_service.domain.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    boolean existsByName(String name);

    Optional<Asset> findByName(String name);
}
