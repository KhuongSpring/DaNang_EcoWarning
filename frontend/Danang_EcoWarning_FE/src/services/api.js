// src/services/api.js
import axios from "axios";

const API_BASE_URL = "http://localhost:8082/api/v1"; // Thay bằng URL của bạn

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// Hàm xử lý lỗi (ĐÂY LÀ HÀM BỊ THIẾU)
const handleError = (error) => {
  console.error("Lỗi API:", error.response || error.message);
  // Ném lỗi ra để component có thể bắt (catch)
  throw error;
};

// Wrapper để xử lý data trả về
const handleResponse = (response) => {
  if (response.data && response.data.status === "SUCCESS") {
    return response.data.data;
  }
  return response.data;
};

// --- API KHU VỰC 1 & 2 ---
export const getAssetMap = (assetType) => {
  return apiClient
    .get(`/asset/map?assetType=${assetType}`)
    .then(handleResponse);
};

export const getAssetProfile = (assetId) => {
  return apiClient.get(`/asset/${assetId}/profile`).then(handleResponse);
};

// --- API KHU VỰC 4 ---
export const getAssetList = (assetType) => {
  return apiClient
    .get(`/asset/asset-list?assetType=${assetType}`)
    .then(handleResponse);
};

// --- API KHU VỰC 3 ---
export const getAssetCountByType = () => {
  return apiClient.get("/static/count-type").then(handleResponse);
};

export const getDisasterDamageByYear = () => {
  return apiClient.get("/static/disaster-damage/by-year").then(handleResponse);
};

export const getDamageDetail = (year) => {
  return apiClient
    .get(`/static/disaster-damage/detail?year=${year}`)
    .then(handleResponse);
};

// export const getAgricultureSummary = (unit = "Tấn") => {
//   return apiClient
//     .get(`/static/agriculture/summary-by-year?unit=${unit}`)
//     .then(handleResponse);
// };
export const getAgricultureSearch = (unit, crop, aspect) => {
  // Sử dụng 'params' để axios tự động mã hóa URL
  const params = {
    unit: unit,
    crop: crop,
    aspect: aspect,
  };
  return apiClient
    .get("/static/agriculture/search", { params })
    .then(handleResponse)
    .catch(handleError);
};
export const getAgricultureFilters = () => {
  return apiClient
    .get("/static/agriculture/filters")
    .then(handleResponse)
    .catch(handleError);
};
