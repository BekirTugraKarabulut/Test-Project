import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom"; // ← useNavigate ekle

export default function Notlarim() {
    const navigate = useNavigate(); // ← navigate hook
    const user = JSON.parse(localStorage.getItem("user"));
    const [dersNotlari, setDersNotlari] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchDersNotlari = async () => {
            try {
                const API_URL = process.env.REACT_APP_API_URL;

                const response = await fetch(
                    `${API_URL}ders-notlarim/list/${user?.username}`,
                    {
                        headers: {
                            "Content-Type": "application/json",
                            "Authorization": `Bearer ${localStorage.getItem("token")}`,
                        },
                    }
                );
                const data = await response.json();
                setDersNotlari(data);
            } catch (err) {
                console.error("Ders notları alınamadı:", err);
            } finally {
                setLoading(false);
            }
        };
        fetchDersNotlari();
    }, [user?.username]);

    const goToHome = () => navigate("/home"); // ← Anasayfa yönlendirme

    return (
        <div style={styles.container}>
            <div style={styles.header}>
                <h2 style={styles.title}>{user?.username} - Notlarım</h2>
                <button style={styles.homeButton} onClick={goToHome}>Anasayfa</button>
            </div>

            {loading ? (
                <p>Yükleniyor...</p>
            ) : dersNotlari.length === 0 ? (
                <p>Henüz ders notu yok.</p>
            ) : (
                <div style={styles.grid}>
                    {dersNotlari.map((not) => (
                        <div key={not.dersNotId} style={styles.card}>
                            <h3 style={styles.cardTitle}>{not.dersNotAdi}</h3>
                            <p style={styles.cardContent}>{not.dersNotIcerik}</p>
                            <p><strong>Fiyat:</strong> {not.dersNotFiyat}</p>
                            <p><strong>Not Türü:</strong> {not.notTuru?.notAdi || "-"}</p>
                            <div style={styles.links}>
                                <a
                                    href={not.dersNotPdf ? `${WEB_SOCKET}${not.dersNotPdf}` : "#"}
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    style={styles.link}
                                >
                                    PDF
                                </a>
                                <a
                                    href={not.dersNotPdfOnizleme ? `${WEB_SOCKET}${not.dersNotPdfOnizleme}` : "#"}
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    style={styles.link}
                                >
                                    Önizleme
                                </a>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

const styles = {
    container: {
        maxWidth: "1000px",
        margin: "0 auto",
        padding: "20px",
        boxSizing: "border-box",
    },
    header: {
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        marginBottom: "30px",
    },
    title: {
        fontSize: "28px",
        color: "#333",
    },
    homeButton: {
        padding: "8px 16px",
        backgroundColor: "#2196F3",
        color: "white",
        border: "none",
        borderRadius: "4px",
        cursor: "pointer",
    },
    grid: {
        display: "grid",
        gridTemplateColumns: "repeat(auto-fill, minmax(250px, 1fr))",
        gap: "20px",
    },
    card: {
        background: "#fff",
        borderRadius: "12px",
        padding: "20px",
        boxShadow: "0 5px 15px rgba(0,0,0,0.1)",
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-between",
    },
    cardTitle: {
        fontSize: "20px",
        fontWeight: "600",
        marginBottom: "10px",
    },
    cardContent: {
        flex: "1",
        marginBottom: "15px",
        color: "#555",
    },
    links: {
        display: "flex",
        justifyContent: "flex-start",
        gap: "10px",
    },
    link: {
        textDecoration: "none",
        color: "#2196F3",
        fontWeight: "500",
    },
};
