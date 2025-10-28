package com.example.search_service.domain.dto.response;

import com.example.search_service.domain.entity.Asset;
import lombok.Data;

@Data
public class AssetMapDTO {

    private String id;
    private String name;
    private String assetType;
    private Double latitude;
    private Double longitude;

    public AssetMapDTO(Asset asset) {
        this.id = asset.getId().toString();
        this.name = asset.getName();
        this.assetType = asset.getAssetType();
        this.latitude = asset.getLatitude();
        this.longitude = asset.getLongitude();
    }
}