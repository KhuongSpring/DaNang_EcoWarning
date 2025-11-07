import React from "react";
import { NavLink } from "react-router-dom";
import "../../styles/components/_sidebar.scss";


import {
  FaChartPie,
  FaMapMarkedAlt,
  FaExclamationTriangle,
} from "react-icons/fa";
import { FaRegSun } from "react-icons/fa6";
import "../../../public/icons/Logo.png";

const Sidebar = () => {
  return (
    <nav className="sidebar">
      <div className="sidebar-logo">
        <img src="../../../public/icons/Logo.png" alt="" />
      </div>
      <ul className="sidebar-nav">
        <li className="sidebar-item">
          <NavLink to="/" className="sidebar-link" end>
            <FaChartPie className="sidebar-icon" />
            <span className="sidebar-text">Dashboard</span>
          </NavLink>
        </li>
        <li className="sidebar-item">
          <NavLink to="/weather" className="sidebar-link">
            <FaRegSun className="sidebar-icon" />
            <span className="sidebar-text">Weather</span>
          </NavLink>
        </li>
        <li className="sidebar-item">
          <NavLink to="/map" className="sidebar-link">
            <FaMapMarkedAlt className="sidebar-icon" />
            <span className="sidebar-text">Map</span>
          </NavLink>
        </li>

        {/* 2. THÊM LINK MỚI VÀO SIDEBAR */}
        <li className="sidebar-item">
          <NavLink to="/report" className="sidebar-link">
            <FaExclamationTriangle className="sidebar-icon" />
            <span className="sidebar-text">Báo cáo sự cố</span>
          </NavLink>
        </li>
      </ul>
    </nav>
  );
};

export default Sidebar;
