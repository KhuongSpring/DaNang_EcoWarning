import React from "react";
import "../../styles/components/_weather-components.scss";

const getWeatherIcon = (iconCode) =>
  `https://openweathermap.org/img/wn/${iconCode}.png`;
const formatTime = (dt) =>
  new Date(dt * 1000).toLocaleTimeString("vi-VN", {
    hour: "2-digit",
    minute: "2-digit",
  });
const formatDay = (dt) =>
  new Date(dt * 1000).toLocaleDateString("vi-VN", { weekday: "short" });

const processDailyData = (list) => {
  const dailyData = {};

  list.forEach((item) => {
    const day = new Date(item.dt * 1000).toLocaleDateString("vi-VN");

    if (!dailyData[day]) {
      dailyData[day] = {
        minTemp: item.main.temp_min,
        maxTemp: item.main.temp_max,
        icons: [],
        hourlyData: [],
      };
    }

    dailyData[day].minTemp = Math.min(
      dailyData[day].minTemp,
      item.main.temp_min
    );
    dailyData[day].maxTemp = Math.max(
      dailyData[day].maxTemp,
      item.main.temp_max
    );

    const hour = new Date(item.dt * 1000).getHours();
    if (hour >= 12 && hour <= 14) {
      dailyData[day].mainIcon = item.weather[0].icon;
    }
    dailyData[day].hourlyData.push(item);
  });

  return Object.values(dailyData)
    .slice(0, 5)
    .map((day) => {
      if (!day.mainIcon) {
        day.mainIcon = day.hourlyData[0].weather[0].icon; 
      }
      return day;
    });
};

const ForecastPanel = ({ forecastList, onDaySelect, selectedDayDt }) => {
  const hourlyData = forecastList.slice(0, 8);
  const dailyData = processDailyData(forecastList);
  return (
    <div className="forecast-panel-card">
      <h4>Dự báo theo giờ</h4>
      <div className="hourly-forecast-scroll">
        {hourlyData.map((item, index) => (
          <div className="hourly-item" key={index}>
            <span>{formatTime(item.dt)}</span>
            <img
              src={getWeatherIcon(item.weather[0].icon)}
              alt={item.weather[0].description}
            />
            <strong>{Math.round(item.main.temp)}°C</strong>
          </div>
        ))}
      </div>
      <h4 style={{ marginTop: "20px" }}>Dự báo 5 ngày</h4>
      <div className="five-day-forecast">
        {dailyData.map((day, index) => {
          const isSelected = selectedDayDt === day.hourlyData[0].dt;
          return (
            <div
              className={`day-item ${isSelected ? "active" : ""}`}
              key={index}
              onClick={() => onDaySelect(isSelected ? null : day.hourlyData)}
            >
              <span>{formatDay(day.hourlyData[0].dt)}</span>
              <img src={getWeatherIcon(day.mainIcon)} alt="" />
              <strong>{Math.round(day.maxTemp)}°</strong>
              <span className="min-temp">{Math.round(day.minTemp)}°</span>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default ForecastPanel;
