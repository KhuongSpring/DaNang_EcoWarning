import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
// Import tệp SCSS tổng của bạn
import "@goongmaps/goong-js/dist/goong-js.css";
import "../src/styles/main.scss";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
