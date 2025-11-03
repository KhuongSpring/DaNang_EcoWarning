// src/components/dashboard/AgricultureChart.jsx
import React, { useEffect, useState } from "react";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  ResponsiveContainer,
  CartesianGrid,
} from "recharts";
import {
  getAgricultureSearch,
  getAgricultureFilters,
} from "../../services/api";

const AgricultureChart = () => {
  const [data, setData] = useState([]);
  const [metricName, setMetricName] = useState("");
  const [selectedUnit, setSelectedUnit] = useState("");
  const [selectedCrop, setSelectedCrop] = useState("");
  const [selectedAspect, setSelectedAspect] = useState("");

  const [unitsList, setUnitsList] = useState([]);
  const [cropsList, setCropsList] = useState([]);
  const [aspectsList, setAspectsList] = useState([]);

  useEffect(() => {
    const fetchFilters = async () => {
      try {
        const filterData = await getAgricultureFilters();
        setUnitsList(filterData.units || []);
        setCropsList(filterData.crops || []);
        setAspectsList(filterData.aspects || []);

        if (filterData.units?.length > 0) {
          setSelectedUnit(filterData.units[4]);
        }
        if (filterData.crops?.length > 0) {
          setSelectedCrop(filterData.crops[5]);
        }
        if (filterData.aspects?.length > 0) {
          setSelectedAspect(filterData.aspects[2]);
        }
      } catch (error) {
        console.error("Lỗi khi lấy danh sách bộ lọc:", error);
      }
    };
    fetchFilters();
  }, []);

  useEffect(() => {
    if (!selectedUnit || !selectedCrop || !selectedAspect) {
      setData([]);
      return;
    }

    const fetchData = async () => {
      try {
        const result = await getAgricultureSearch(
          selectedUnit,
          selectedCrop,
          selectedAspect
        );
        setData(result || []);
        setMetricName(result[0]?.metricName || "Không có dữ liệu");
      } catch (error) {
        console.error("Lỗi khi lấy dữ liệu nông nghiệp:", error);
        setData([]);
      }
    };

    fetchData();
  }, [selectedUnit, selectedCrop, selectedAspect]);

  return (
    <>
      <div className="chart-filters-container">
        <div className="filter-group">
          <label htmlFor="unit-select">Đơn vị:</label>
          <select
            id="unit-select"
            value={selectedUnit}
            onChange={(e) => setSelectedUnit(e.target.value)}
          >
            <option value="">-- Chọn đơn vị --</option>
            {unitsList.map((unit) => (
              <option key={unit} value={unit}>
                {unit}
              </option>
            ))}
          </select>
        </div>

        <div className="filter-group">
          <label htmlFor="crop-select">Cây trồng:</label>
          <select
            id="crop-select"
            value={selectedCrop}
            onChange={(e) => setSelectedCrop(e.target.value)}
          >
            <option value="">-- Chọn cây trồng --</option>
            {cropsList.map((crop) => (
              <option key={crop} value={crop}>
                {crop}
              </option>
            ))}
          </select>
        </div>

        <div className="filter-group">
          <label htmlFor="aspect-select">Tiêu chí:</label>
          <select
            id="aspect-select"
            value={selectedAspect}
            onChange={(e) => setSelectedAspect(e.target.value)}
          >
            <option value="">-- Chọn tiêu chí --</option>
            {aspectsList.map((aspect) => (
              <option key={aspect} value={aspect}>
                {aspect}
              </option>
            ))}
          </select>
        </div>
      </div>

      <ResponsiveContainer width="100%" height={250}>
        <LineChart
          data={data}
          margin={{ top: 5, right: 20, left: 10, bottom: 5 }}
        >
          <CartesianGrid strokeDasharray="3 3" stroke="#e0e0e0" />
          <XAxis dataKey="year" />
          <YAxis />
          <Tooltip />
          <Legend />
          <Line
            type="monotone"
            dataKey="totalValue"
            name={metricName}
            stroke="#3b82f6"
            strokeWidth={2}
            activeDot={{ r: 6 }}
          />
        </LineChart>
      </ResponsiveContainer>
    </>
  );
};

export default AgricultureChart;
