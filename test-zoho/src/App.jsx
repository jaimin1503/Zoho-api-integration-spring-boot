import { useState } from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";

import Home from "./pages/Home.jsx";
import GetCode from "./pages/GetCode";
import GotCode from "./pages/GotCode";


function App() {

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/get-code" element={<GetCode />} />
        <Route path="/got-code" element={<GotCode />} />
      </Routes>
    </Router>
  );
}

export default App;
