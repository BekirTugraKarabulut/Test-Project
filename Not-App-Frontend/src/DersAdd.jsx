import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function DersEkle() {
    const navigate = useNavigate();
    const user = JSON.parse(localStorage.getItem("user"));

    const [form, setForm] = useState({
        dersId: "",
        dersAdi: "",
        ogrenciUsername: user?.username || ""
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm({ ...form, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`${API_URL}ders/save`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${localStorage.getItem("token")}`
                },
                body: JSON.stringify(form)
            });

            const data = await response.json();

            if (response.ok) {
                alert("✅ Ders başarıyla eklendi!");
                navigate("/home");
            } else {
                alert("❌ Hata: " + (data?.message || "Ders eklenemedi"));
            }
        } catch (err) {
            console.error(err);
            alert("❌ Sunucuya bağlanırken hata oluştu!");
        }
    };

    return (
        <div style={{ maxWidth: "600px", margin: "50px auto" }}>
            <h2>Ders Ekle</h2>
            <form onSubmit={handleSubmit}>
                <input
                    name="dersId"
                    placeholder="Ders ID"
                    value={form.dersId}
                    onChange={handleChange}
                    required
                    style={{ display: "block", marginBottom: "10px", width: "100%", padding: "8px" }}
                />

                <input
                    name="dersAdi"
                    placeholder="Ders Adı"
                    value={form.dersAdi}
                    onChange={handleChange}
                    required
                    style={{ display: "block", marginBottom: "10px", width: "100%", padding: "8px" }}
                />

                <input
                    name="ogrenciUsername"
                    placeholder="Username"
                    value={form.ogrenciUsername}
                    onChange={handleChange}
                    required
                    style={{ display: "block", marginBottom: "10px", width: "100%", padding: "8px" }}
                />

                <button type="submit" style={{ padding: "10px 20px", marginTop: "10px" }}>
                    Ders Ekle
                </button>
            </form>
        </div>
    );
}
