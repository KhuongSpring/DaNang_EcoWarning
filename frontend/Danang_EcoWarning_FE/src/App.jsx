// src/App.jsx
import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainLayout from "./layout/MainLayout";
import DashboardPage from "./pages/DashboardPage/DashboardPage";
import MapPage from "./pages/Map/MapPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Tất cả các route bên trong sẽ dùng MainLayout */}
        <Route element={<MainLayout />}>
          {/* Trang Dashboard (Khu vực 3) */}
          <Route path="/" element={<DashboardPage />} />

          {/* Trang Bản đồ (Khu vực 1, 2, 4) */}
          <Route path="/map" element={<MapPage />} />

          {/* Bạn có thể thêm các trang khác ở đây */}
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
