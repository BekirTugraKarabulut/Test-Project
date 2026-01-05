import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function ProfileUpdate() {
    const navigate = useNavigate();

    const storedUser = JSON.parse(localStorage.getItem("user"));

    const [form, setForm] = useState({
        ogrenciAdi: storedUser?.ogrenciAdi || "",
        ogrenciSoyadi: storedUser?.ogrenciSoyadi || "",
        ogrenciEmail: storedUser?.ogrenciEmail || "",
        bolumAdi: storedUser?.dtoBolum?.bolumAdi || "",
        fakulteAdi: storedUser?.dtoBolum?.dtoFakulte?.fakulteAdi || "",
    });

    const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const API_URL = process.env.REACT_APP_API_URL;

            const response = await fetch(
                `${API_URL}/guncelle/profil-bilgi/${storedUser.username}`,
                {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`,
                    },
                    body: JSON.stringify({
                        ogrenciAdi: form.ogrenciAdi,
                        ogrenciSoyadi: form.ogrenciSoyadi,
                        ogrenciEmail: form.ogrenciEmail,
                        dtoBolum: {
                            bolumAdi: form.bolumAdi,
                            fakulte: { fakulteAdi: form.fakulteAdi },
                        },
                    }),
                }
            );

            const text = await response.text();
            const data = text ? JSON.parse(text) : null;

            if (response.ok) {
                alert("✅ Profil güncelleme başarılı!");
                if (data) localStorage.setItem("user", JSON.stringify(data));
                navigate("/");
            } else {
                alert("❌ Hata: " + (data?.message || "Güncelleme başarısız"));
            }
        } catch (err) {
            console.error(err);
            alert("❌ Sunucuya bağlanırken hata oluştu!");
        }
    };

    return (
        <div style={styles.container}>
            <div style={styles.card}>
                <h2 style={styles.title}>Profil Güncelle</h2>
                <form onSubmit={handleSubmit} style={styles.form}>
                    <input
                        name="ogrenciAdi"
                        placeholder="Adı"
                        value={form.ogrenciAdi}
                        onChange={handleChange}
                        required
                        style={styles.input}
                    />
                    <input
                        name="ogrenciSoyadi"
                        placeholder="Soyadı"
                        value={form.ogrenciSoyadi}
                        onChange={handleChange}
                        required
                        style={styles.input}
                    />
                    <input
                        name="ogrenciEmail"
                        type="email"
                        placeholder="Email"
                        value={form.ogrenciEmail}
                        onChange={handleChange}
                        required
                        style={styles.input}
                    />
                    <input
                        name="bolumAdi"
                        placeholder="Bölüm"
                        value={form.bolumAdi}
                        onChange={handleChange}
                        required
                        style={styles.input}
                    />
                    <input
                        name="fakulteAdi"
                        placeholder="Fakülte"
                        value={form.fakulteAdi}
                        onChange={handleChange}
                        required
                        style={styles.input}
                    />
                    <button type="submit" style={styles.button}>
                        Güncelle
                    </button>
                </form>
            </div>
        </div>
    );
}

const styles = {
    container: {
        minHeight: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "flex-start",
        padding: "40px 20px",
        boxSizing: "border-box",
    },
    card: {
        width: "100%",
        maxWidth: "600px",
        background: "#fff",
        padding: "30px 40px",
        borderRadius: "12px",
        boxShadow: "0 8px 25px rgba(0,0,0,0.15)",
    },
    title: {
        textAlign: "center",
        fontSize: "26px",
        fontWeight: "600",
        marginBottom: "25px",
        color: "#333",
    },
    form: {
        display: "flex",
        flexDirection: "column",
        gap: "15px",
    },
    input: {
        padding: "12px 15px",
        borderRadius: "8px",
        border: "1px solid #ccc",
        fontSize: "16px",
        outline: "none",
        transition: "all 0.2s ease",
        boxSizing: "border-box",
    },
    button: {
        marginTop: "20px",
        padding: "12px",
        borderRadius: "8px",
        border: "none",
        background: "#2196F3",
        color: "#fff",
        fontSize: "16px",
        fontWeight: "600",
        cursor: "pointer",
        transition: "all 0.2s ease",
    },
};
