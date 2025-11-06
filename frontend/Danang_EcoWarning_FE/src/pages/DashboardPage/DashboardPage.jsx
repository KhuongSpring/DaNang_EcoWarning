import React, { useState } from "react";
import AssetTypeChart from "../../components/dashboard/AssetTypeChart";
import DamageChart from "../../components/dashboard/DamageChart";
import AgricultureChart from "../../components/dashboard/AgricultureChart";

import DamageDetailPanel, {
  DamageDetailPlaceholder,
} from "../../components/dashboard/DamageDetailPanel";

import { getDamageDetail } from "../../services/api";

import "../../styles/pages/_dashboard-page.scss";

const DashboardPage = () => {
  const [damageDetailData, setDamageDetailData] = useState(null);
  const [selectedYear, setSelectedYear] = useState(null);

  const handleBarClick = async (year) => {
    if (year === selectedYear) {
      setSelectedYear(null);
      setDamageDetailData(null);
      return;
    }

    try {
      const data = await getDamageDetail(year);
      setDamageDetailData(data);
      setSelectedYear(year);
    } catch (error) {
      console.error("Lỗi khi lấy chi tiết thiệt hại:", error);
    }
  };

  return (
    <div className="dashboard-page">
      <h1 className="dashboard-title">Dashboard</h1>

      <div className="dashboard-grid">
        {/* <div className="chart-container col-span-4">
          <h3>Phân loại Tài sản</h3>
          <AssetTypeChart />
        </div> */}

        <div className="chart-container col-span-12">
          <h3>Sản lượng Nông nghiệp (Tấn)</h3>
          <AgricultureChart />
        </div>

        <div className="chart-container col-span-7">
          <h3>Thiệt hại Thiên tai (Theo năm)</h3>
          <p className="chart-subtitle">Click vào một cột để xem chi tiết</p>
          <DamageChart onBarClick={handleBarClick} />
        </div>

        <div className="col-span-5">
          {selectedYear && damageDetailData ? (
            <DamageDetailPanel year={selectedYear} data={damageDetailData} />
          ) : (
            <DamageDetailPlaceholder />
          )}
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;
