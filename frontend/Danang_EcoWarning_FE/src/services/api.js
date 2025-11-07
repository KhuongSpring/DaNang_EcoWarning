import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
const API_REPORT_URL = import.meta.env.VITE_API_REPORT_URL;
const OPENWEATHER_API_KEY = import.meta.env.VITE_OPENWEATHER_API_KEY;
const OPENWEATHER_BASE_URL = import.meta.env.VITE_OPENWEATHER_BASE_URL;

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});
const apiClientv2 = axios.create({
  baseURL: API_REPORT_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

const handleError = (error) => {
  console.error("Lỗi API:", error.response || error.message);
  throw error;
};
const handleResponse = (response) => {
  if (response.data && response.data.status === "SUCCESS") {
    return response.data.data;
  }
  return response.data;
};

export const getAssetMap = (assetType) => {
  return apiClient
    .get(`/asset/map?assetType=${assetType}`)
    .then(handleResponse);
};

export const getAssetProfile = (assetId) => {
  return apiClient.get(`/asset/${assetId}/profile`).then(handleResponse);
};

export const getAssetList = (assetType) => {
  return apiClient
    .get(`/asset/asset-list?assetType=${assetType}`)
    .then(handleResponse);
};

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

export const getAgricultureSearch = (unit, crop, aspect) => {
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

/**
 * @param {number | string} assetId
 */
export const getMetricsByAsset = (assetId) => {
  return apiClient
    .get(`/metric/by-asset/${assetId}`)
    .then(handleResponse)
    .catch(handleError);
};

/**
 * @param {number | string} assetId
 * @param {number | string} metricId
 * @param {string} fromDate
 * @param {string} toDate
 */
export const getMetricHistory = (assetId, metricId, fromDate, toDate) => {
  const params = {
    assetId: assetId,
    metricId: metricId,
    fromDate: fromDate,
    toDate: toDate,
  };
  Object.keys(params).forEach(
    (key) => params[key] === undefined && delete params[key]
  );

  return apiClient
    .get("/metric/history", { params })
    .then(handleResponse)
    .catch(handleError);
};

export const getCurrentWeather = async (lat, lon) => {
  const url = `${OPENWEATHER_BASE_URL}/weather?lat=16.054407&lon=108.202164`;
  try {
    const response = await axios.get(url, {
      params: {
        lat: lat,
        lon: lon,
        appid: OPENWEATHER_API_KEY,
        units: "metric",
        lang: "vi",
      },
    });
    return response.data;
  } catch (error) {
    console.error("Lỗi khi lấy thời tiết hiện tại:", error);
    throw error;
  }
};

export const getForecastWeather = async (lat, lon) => {
  const url = `${OPENWEATHER_BASE_URL}/forecast?lat=16.054407&lon=108.202164`;
  try {
    const response = await axios.get(url, {
      params: {
        lat: lat,
        lon: lon,
        appid: OPENWEATHER_API_KEY,
        units: "metric",
        lang: "vi",
      },
    });
    return { list: response.data.list, city: response.data.city };
  } catch (error) {
    console.error("Lỗi khi lấy dự báo:", error);
    throw error;
  }
};

export const getAirPollution = async (lat, lon) => {
  const url = `${OPENWEATHER_BASE_URL}/air_pollution?lat=16.054407&lon=108.202164`;
  try {
    const response = await axios.get(url, {
      params: {
        lat: lat,
        lon: lon,
        appid: OPENWEATHER_API_KEY,
      },
    });
    return response.data.list[0];
  } catch (error) {
    console.error("Lỗi khi lấy dữ liệu ô nhiễm:", error);
    throw error;
  }
};
/**
 * 
 * @param {object} reportData - Dữ liệu báo cáo theo format { request: {...}, image: "..." }
 */
export const sendDisasterReport = (reportData) => {
  return apiClientv2
    .post("/report/send-report", reportData)
    .then(handleResponse)
    .catch(handleError);
};
