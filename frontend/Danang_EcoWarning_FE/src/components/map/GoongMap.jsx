import React, { useRef, useEffect } from "react";
import { createRoot } from "react-dom/client";
import goongSdk from "@goongmaps/goong-js";
import { getAssetMap } from "../../services/api";

import {
  FaMapMarkerAlt,
  FaHome,
  FaWater,
  FaMountain,
  FaBuilding,
  FaExclamationTriangle,
  FaCloudRain,
} from "react-icons/fa";

const ASSET_STYLE_MAP = {
  "Khu Vực Sạt Lở": { icon: FaMountain, color: "#E67E22" },
  "Nhà Trú Bão": { icon: FaHome, color: "#3498DB" },
  "Hồ Ao": { icon: FaWater, color: "#1ABC9C" },
  "Hồ Thủy Lợi": { icon: FaBuilding, color: "#9B59B6" },
  "Trạm Cảnh Báo Ven Biển": { icon: FaExclamationTriangle, color: "#F1C40F" },
  "Trạm Đo Mưa": { icon: FaCloudRain, color: "#7F8C8D" },
  default: { icon: FaMapMarkerAlt, color: "#C0392B" },
};
const ICON_SIZE = "24px";

const GoongMap = ({ apiKey, assetType, onMarkerClick }) => {
  const mapContainer = useRef(null);
  const map = useRef(null);
  const markersRef = useRef([]);

  if (apiKey) {
    goongSdk.accessToken = apiKey;
  }

  useEffect(() => {
    if (map.current || !mapContainer.current) return;
    map.current = new goongSdk.Map({
      container: mapContainer.current,
      style: "https://tiles.goong.io/assets/goong_map_web.json",
      center: [108.2208, 16.0471],
      zoom: 12,
    });
    map.current.addControl(new goongSdk.NavigationControl());
  }, []);

  useEffect(() => {
    if (!map.current || !assetType) {
      return;
    }

    let isCancelled = false;

    console.log(`Đang dọn dẹp ${markersRef.current.length} icon cũ...`);
    markersRef.current.forEach(({ marker, root }) => {
      root.unmount();
      marker.remove();
    });
    markersRef.current = [];

    const fetchAndDrawAssets = async () => {
      try {
        console.log(`Bắt đầu tải: ${assetType}`);
        const assets = await getAssetMap(assetType);

        if (isCancelled) {
          console.log(`ĐÃ HỦY (ignored): ${assetType}`);
          return;
        }

        if (!assets || assets.length === 0) {
          console.warn(`Không tìm thấy asset cho: ${assetType}`);
          return;
        }

        console.log(`Đang vẽ: ${assetType}`);
        const newMarkersAndRoots = [];

        assets.forEach((asset) => {
          const lat = parseFloat(asset.latitude);
          const lng = parseFloat(asset.longitude);

          console.log("tọa độ: ", lat, lng);

          if (
            isNaN(lat) ||
            isNaN(lng) || 
            lat < -90 ||
            lat > 90 || 
            lng < -180 ||
            lng > 180 
          ) {
            console.warn("Bỏ qua asset có tọa độ không hợp lệ:", asset.name, {
              lat,
              lng,
            });
            return; 
          }
          const style =
            ASSET_STYLE_MAP[asset.assetType] || ASSET_STYLE_MAP.default;
          const IconComponent = style.icon;
          const iconColor = style.color;
          const el = document.createElement("div");
          el.className = "react-icon-marker";
          const root = createRoot(el);
          root.render(
            <IconComponent
              style={{
                color: iconColor,
                fontSize: ICON_SIZE,
                filter: "drop-shadow(0 1px 2px rgba(0,0,0,0.3))",
              }}
            />
          );

          const marker = new goongSdk.Marker(el)
            .setLngLat([lng, lat]) 
            .addTo(map.current);

          const popup = new goongSdk.Popup({ offset: 25 }).setHTML(
            `<h3>${asset.name}</h3><p>${asset.assetType}</p>`
          );
          marker.setPopup(popup);

          el.addEventListener("click", (e) => {
            e.stopPropagation();
            onMarkerClick(asset.id);
            map.current.flyTo({
              center: [lng, lat], 
              zoom: 15,
            });
          });

          newMarkersAndRoots.push({ marker, root });
        });

        if (!isCancelled) {
          markersRef.current = newMarkersAndRoots;
        }
      } catch (error) {
        console.error(`Lỗi khi tải ${assetType}:`, error);
      }
    };

    fetchAndDrawAssets();

    return () => {
      console.log(`Cleanup effect của: ${assetType}`);
      isCancelled = true;
    };
  }, [assetType]);

  return <div ref={mapContainer} className="map-view" />;
};

export default GoongMap;
