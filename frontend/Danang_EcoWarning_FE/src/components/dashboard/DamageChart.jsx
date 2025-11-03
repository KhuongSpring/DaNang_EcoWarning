// src/components/dashboard/DamageChart.jsx
import React, { useEffect, useState } from "react";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  ResponsiveContainer,
  CartesianGrid,
} from "recharts";
import { getDisasterDamageByYear } from "../../services/api";

const DamageChart = ({ onBarClick }) => {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const result = await getDisasterDamageByYear();
        const chartData = result.map((item) => ({
          name: item.year,
          "Tổng thiệt hại (tỷ đồng)": item.totalValue,
        }));
        setData(chartData);
      } catch (error) {
        console.error("Lỗi khi lấy dữ liệu thiệt hại:", error);
      }
    };
    fetchData();
  }, []);

  // --- THAY ĐỔI HÀM NÀY ---
  const handleChartClick = (chartData) => {
    // 'activeLabel' là nhãn trên trục X (ví dụ: "2020")
    // mà con trỏ chuột đang ở trên.
    if (chartData && chartData.activeLabel) {
      const year = chartData.activeLabel; // Lấy năm trực tiếp từ activeLabel

      if (year) {
        onBarClick(year); // Gọi hàm của cha với năm (year)
      }
    }
  };
  // --- KẾT THÚC THAY ĐỔI ---

  return (
    <ResponsiveContainer width="100%" height={250}>
      <BarChart
        data={data}
        margin={{ top: 5, right: 20, left: 10, bottom: 5 }}
        onClick={handleChartClick}
        cursor="pointer"
      >
        <CartesianGrid strokeDasharray="3 3" stroke="#e0e0e0" />
        <XAxis dataKey="name" />
        <YAxis />
        <Tooltip />
        <Legend />
        <Bar dataKey="Tổng thiệt hại (tỷ đồng)" fill="#ef4444" barSize={40} />
      </BarChart>
    </ResponsiveContainer>
  );
};

export default DamageChart;
