// src/components/common/SearchBar.jsx
import React, { useState, useEffect, useRef } from "react";
import { getAssetList } from "../../services/api";
import "../../styles/components/_searchbar.scss";

// Các loại asset không có tọa độ (Hardcode)
const SEARCHABLE_ASSET_TYPES = ["Sông", "Hồ Điều Hòa"]; // Thay bằng các type của bạn

const SearchBar = ({ onAssetSelect }) => {
  const [query, setQuery] = useState("");
  const [results, setResults] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const debounceTimeout = useRef(null);

  useEffect(() => {
    // Hủy timeout cũ
    if (debounceTimeout.current) {
      clearTimeout(debounceTimeout.current);
    }

    if (query.length < 2) {
      setResults([]);
      return;
    }

    // Đặt timeout mới (debouncing)
    debounceTimeout.current = setTimeout(async () => {
      setIsLoading(true);
      const allResults = [];

      // Gọi API cho từng loại asset
      try {
        for (const type of SEARCHABLE_ASSET_TYPES) {
          const assets = await getAssetList(type);
          // Lọc kết quả dựa trên query
          const filtered = assets.filter((asset) =>
            asset.name.toLowerCase().includes(query.toLowerCase())
          );
          allResults.push(...filtered);
        }
        setResults(allResults);
      } catch (error) {
        console.error("Lỗi khi tìm kiếm:", error);
      }
      setIsLoading(false);
    }, 500); // Chờ 500ms
  }, [query]);

  const handleSelect = (assetId) => {
    onAssetSelect(assetId); // Gửi ID lên MapPage
    setQuery("");
    setResults([]);
  };

  return (
    <div className="search-bar-container">
      <input
        type="text"
        className="search-input"
        placeholder="Tìm kiếm Sông, Hồ điều hòa..."
        value={query}
        onChange={(e) => setQuery(e.target.value)}
      />
      {isLoading && <div className="spinner"></div>}
      {results.length > 0 && (
        <ul className="search-results">
          {results.map((asset) => (
            <li key={asset.id} onClick={() => handleSelect(asset.id)}>
              <strong>{asset.name}</strong> ({asset.assetType})
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default SearchBar;
