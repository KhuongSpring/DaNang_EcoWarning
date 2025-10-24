package com.example.collector_data_service.domain.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "assets")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true, length = 512)
    String name;

    @Column(nullable = false)
    String assetType;

    String district;

    String ward;

    @Column(columnDefinition = "TEXT")
    String address;

    Double latitude;

    Double longitude;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    String attributes;
}