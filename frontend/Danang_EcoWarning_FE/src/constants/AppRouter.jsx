import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { ROUTES } from "../constants/routes";

import MainLayout from "../layout/MainLayout";

import MapPage from "../pages/Map/MapPage";

const AppRouter = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<MainLayout />}>
          <Route path={ROUTES.MAP} element={<MapPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
