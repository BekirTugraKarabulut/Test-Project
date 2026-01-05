import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function Register() {
    const [form, setForm] = useState({
        username: "",
        ogrenciSifre: "",
        ogrenciAdi: "",
        ogrenciSoyadi: "",
        ogrenciEmail: "",
        bolumId: "",
    });

    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        // Arka plan tam ekran ve scroll yok
        document.body.style.margin = "0";
        document.body.style.padding = "0";
        document.body.style.height = "100vh";
        document.body.style.overflow = "hidden";
        document.documentElement.style.height = "100%";
    }, []);

    const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });
    const API_URL = process.env.REACT_APP_API_URL;
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const res = await fetch(`${API_URL}/register`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(form),
            });

            if (res.ok) {
                setMessage("✅ Kayıt başarılı!");
                setForm({
                    username: "",
                    ogrenciSifre: "",
                    ogrenciAdi: "",
                    ogrenciSoyadi: "",
                    ogrenciEmail: "",
                    bolumId: "",
                });
            } else {
                const data = await res.json();
                setMessage("❌ Hata: " + (data.message || "Bilinmeyen hata"));
            }
        } catch (err) {
            setMessage("❌ Sunucuya ulaşılamıyor: " + err.message);
        }
    };

    return (
        <div style={styles.container}>
            <div style={styles.card}>
                <h2 style={styles.title}>Kayıt Ol</h2>
                <form onSubmit={handleSubmit} style={styles.form}>
                    {[
                        { name: "username", placeholder: "Kullanıcı Adı" },
                        { name: "ogrenciSifre", placeholder: "Şifre", type: "password" },
                        { name: "ogrenciAdi", placeholder: "Adı" },
                        { name: "ogrenciSoyadi", placeholder: "Soyadı" },
                        { name: "ogrenciEmail", placeholder: "Email", type: "email" },
                        { name: "bolumId", placeholder: "Bölüm ID", type: "number" },
                    ].map(field => (
                        <input
                            key={field.name}
                            name={field.name}
                            type={field.type || "text"}
                            placeholder={field.placeholder}
                            value={form[field.name]}
                            onChange={handleChange}
                            required
                            style={styles.input}
                        />
                    ))}

                    <button type="submit" style={styles.submitButton}>
                        Kayıt Ol
                    </button>
                </form>

                {message && <p id="registerMessage" style={{ marginTop: "15px", color: message.includes("✅") ? "green" : "red" }}>{message}</p>}

                <button
                    onClick={() => navigate("/login")}
                    style={styles.loginButton}
                >
                    Login'e geç
                </button>
            </div>
        </div>
    );
}

const styles = {
    container: {
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
        width: "100vw",
        background: "linear-gradient(135deg, #6B73FF 0%, #000DFF 100%)",
    },
    card: {
        backgroundColor: "white",
        padding: "40px",
        borderRadius: "12px",
        boxShadow: "0 8px 20px rgba(0,0,0,0.2)",
        width: "100%",
        maxWidth: "400px",
        textAlign: "center",
    },
    title: {
        marginBottom: "20px",
        color: "#333",
        fontSize: "28px",
        fontWeight: "600",
    },
    form: {
        display: "flex",
        flexDirection: "column",
        gap: "15px",
    },
    input: {
        padding: "12px",
        borderRadius: "8px",
        border: "1px solid #ccc",
        fontSize: "14px",
        outline: "none",
    },
    submitButton: {
        padding: "12px",
        backgroundColor: "#6B73FF",
        color: "white",
        border: "none",
        borderRadius: "8px",
        cursor: "pointer",
        fontWeight: "bold",
        fontSize: "16px",
        transition: "background 0.3s",
    },
    loginButton: {
        marginTop: "20px",
        padding: "10px 20px",
        backgroundColor: "#FF9800",
        color: "white",
        border: "none",
        borderRadius: "8px",
        cursor: "pointer",
        fontWeight: "bold",
        transition: "background 0.3s",
    },
};
