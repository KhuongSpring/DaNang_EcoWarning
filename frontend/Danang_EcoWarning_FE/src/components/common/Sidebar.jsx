
import React from "react";
import { NavLink } from "react-router-dom";
import "../../styles/components/_sidebar.scss"; 

import { FaChartPie, FaMapMarkedAlt } from "react-icons/fa";

const Sidebar = () => {
  return (
    <nav className="sidebar">
      <div className="sidebar-logo"></div>
      <ul className="sidebar-nav">
        <li className="sidebar-item">
          <NavLink to="/" className="sidebar-link">
            <FaChartPie className="sidebar-icon" />
            <span className="sidebar-text">Dashboard Tổng quan</span>
          </NavLink>
        </li>
        <li className="sidebar-item">
          <NavLink to="/map" className="sidebar-link">
            <FaMapMarkedAlt className="sidebar-icon" />
            <span className="sidebar-text">Bản đồ Tương tác</span>
          </NavLink>
        </li>
      </ul>
    </nav>
  );
};

export default Sidebar;
