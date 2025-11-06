// src/main.jsx
import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";

// Import CSS của Goong Maps (Rất quan trọng)
import "@goongmaps/goong-js/dist/goong-js.css";

// Import file SASS chính
import "./styles/main.scss";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
