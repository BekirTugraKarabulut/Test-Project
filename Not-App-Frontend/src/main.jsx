import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Register from "./Register.jsx";
import Login from "./Login.jsx";
import Home from "./Home.jsx";
import ProfileUpdate from "./ProfileUpdate.jsx";
import NotAdd from "./NotAdd.jsx";
import DersAdd from "./DersAdd.jsx";
import Notlarim from "./Notlarim.jsx";
import Favorilerim from "./Favorilerim.jsx";

ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Navigate to="/register" />} />
                <Route path="/register" element={<Register />} />
                <Route path="/login" element={<Login />} />
                <Route path="/home" element={<Home />} />
                <Route path="/profile-update" element={<ProfileUpdate />} />
                <Route path="/ders-notu-ekle" element={<NotAdd />} />
                <Route path="/ders-ekle" element={<DersAdd />} />
                <Route path="/notlarim" element={<Notlarim />} />
                <Route path="/favorilerim" element={<Favorilerim />} />
            </Routes>
        </BrowserRouter>
    </React.StrictMode>
);
