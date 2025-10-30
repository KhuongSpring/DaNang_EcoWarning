import React from "react";
import { Outlet } from "react-router-dom";
import "./MainLayout.scss";


const MainLayout = () => {
  return (
    <div className="main-layout-new">
      <main className="main-content-new">
        <Outlet /> 
      </main>
    </div>
  );
};

export default MainLayout;
