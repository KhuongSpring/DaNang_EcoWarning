import React from "react";
import { Outlet } from "react-router-dom";
import Sidebar from "../components/common/Sidebar";
import Footer from "../components/common/Footer";

import "./MainLayout.scss";

const MainLayout = () => {
  return (
    <div className="app-layout">
      <Sidebar />

      <div className="main-content-wrapper">
        <main className="main-content">
          <Outlet />
        </main>
        <Footer />
      </div>
    </div>
  );
};

export default MainLayout;
