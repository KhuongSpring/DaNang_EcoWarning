import React from "react";
import "./AlertsPage.scss";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faWater,
  faMountainSun,
  faTemperatureHigh,
} from "@fortawesome/free-solid-svg-icons";

const mockAlerts = [
  {
    id: 1,
    type: "warning",
    title: "Cảnh báo Ngập lụt Sông Cẩm Lệ",
    message: "Mô hình dự đoán mực nước có thể vượt báo động 2 trong 24h tới.",
    icon: faWater,
  },
  {
    id: 2,
    type: "danger",
    title: "Nguy cơ Sạt lở cao tại Hòa Vang",
    message: "Dự báo lượng mưa lớn kết hợp với dữ liệu địa chất lịch sử.",
    icon: faMountainSun,
  },
  {
    id: 3,
    type: "info",
    title: "Cảnh báo Nắng nóng",
    message: "Nhiệt độ dự kiến đạt 39°C. Hạn chế ra ngoài trời.",
    icon: faTemperatureHigh,
  },
];

const AlertsPage = () => {
  return (
    <div className="alerts-page">
      <h1>Hệ thống Cảnh báo sớm</h1>
      <p>
        Các cảnh báo được tạo ra từ mô hình chẩn đoán dựa trên dữ liệu khí hậu
        và lịch sử thiên tai.
      </p>

      <div className="alert-list">
        {mockAlerts.map((alert) => (
          <div
            key={alert.id}
            className={`alert-item alert-item--${alert.type}`}
          >
            <div className="alert-item__icon">
              <FontAwesomeIcon icon={alert.icon} />
            </div>
            <div className="alert-item__content">
              <h3>{alert.title}</h3>
              <p>{alert.message}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default AlertsPage;
