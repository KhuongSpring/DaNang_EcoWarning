import React, { useState, useEffect } from "react";
import ReactMapGL, {
  Marker,
  Popup,
  NavigationControl,
} from "@goongmaps/goong-map-react";
import mapConfig from "../../configs/map.config";
import { dataService } from "../../services/dataService";
import "./MapEmbed.scss"; // SCSS riêng cho bản đồ nhúng

const GOONG_MAP_TILES_KEY = import.meta.env.VITE_GOONG_MAP_TILES_KEY;

const MapEmbed = () => {
  const [viewport, setViewport] = useState({
    // Width/Height sẽ do CSS container quyết định
    latitude: mapConfig.daNangCenter[0],
    longitude: mapConfig.daNangCenter[1],
    zoom: mapConfig.defaultZoom - 1, // Zoom nhỏ hơn một chút
  });

  const [landslideData, setLandslideData] = useState([]);
  const [selectedSite, setSelectedSite] = useState(null);

  useEffect(() => {
    dataService.getLandslideData().then((data) => {
      const cleanedData = data.filter((site) => site.lat && site.lng);
      setLandslideData(cleanedData);
    });
  }, []);

  return (
    // Container này QUAN TRỌNG, cần có height trong CSS
    <div className="map-embed-container">
      <ReactMapGL
        {...viewport}
        width="100%" // Luôn fill container
        height="100%" // Luôn fill container
        goongApiAccessToken={GOONG_MAP_TILES_KEY}
        // mapStyle={mapConfig.goongMapStyle} // Dùng style default hoặc satellite tùy ý
        mapStyle={mapConfig.goongSatelliteStyle}
        onViewportChange={(nextViewport) => setViewport(nextViewport)}
      >
        <NavigationControl style={{ top: 10, right: 10 }} />

        {landslideData.map((site, index) => (
          <Marker
            key={`slide-${index}-${site.lat}-${site.lng}`}
            latitude={site.lat}
            longitude={site.lng}
          >
            <button
              className="marker-btn marker-danger"
              onClick={(e) => {
                e.preventDefault();
                setSelectedSite(site);
              }}
            >
              <span role="img" aria-label="marker">
                📍
              </span>
            </button>
          </Marker>
        ))}

        {selectedSite && (
          <Popup
            latitude={selectedSite.lat}
            longitude={selectedSite.lng}
            onClose={() => setSelectedSite(null)}
            closeOnClick={true}
            anchor="bottom"
          >
            <div>
              <b>{selectedSite.phan_loai?.trim() || "Điểm Sạt lở"}</b>
              <br />
              Vị trí: {selectedSite.vi_tri} ({selectedSite.phuong},{" "}
              {selectedSite.quan})
            </div>
          </Popup>
        )}
      </ReactMapGL>
    </div>
  );
};

export default MapEmbed;
