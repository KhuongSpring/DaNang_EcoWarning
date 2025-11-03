
import React from "react";
import "../../styles/components/_modal.scss";

const DamageDetailModal = ({ year, data, onClose }) => {
  if (!data) return null;

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <button onClick={onClose} className="close-btn">
          &times;
        </button>
        <h3>Chi tiết Thiệt hại Năm {year}</h3>

        <div className="modal-list">
          {data.map((item, index) => (
            <div className="list-item" key={index}>
              <span>{item.metricName}</span>
              <strong>
                {item.value} {item.unit}
              </strong>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default DamageDetailModal;
