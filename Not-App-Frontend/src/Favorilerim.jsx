import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Favorilerim() {
    const user = JSON.parse(localStorage.getItem("user"));
    const navigate = useNavigate();
    const [favoriler, setFavoriler] = useState([]);
    const [loading, setLoading] = useState(true);

    const handleLogout = () => {
        localStorage.removeItem("user");
        localStorage.removeItem("token");
        navigate("/login");
    };

    const goHome = () => navigate("/home");

    // Favorileri çek
    useEffect(() => {
        const fetchFavoriler = async () => {
            try {
                const API_URL = process.env.REACT_APP_API_URL;
                const response = await fetch(`${API_URL}/begen/${user.username}`, {
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`
                    }
                });
                if (response.ok) {
                    const data = await response.json(); // backend DtoBegen listesi dönmeli
                    setFavoriler(data);
                } else {
                    console.error("Favoriler alınamadı");
                }
            } catch (err) {
                console.error("Favoriler isteği başarısız:", err);
            } finally {
                setLoading(false);
            }
        };
        fetchFavoriler();
    }, [user.username]);

    // Favoriden kaldır
    const removeFavori = async (begenId) => {
        try {
            const API_URL = process.env.REACT_APP_API_URL;

            const response = await fetch(`${API_URL}/begen/delete/${begenId}`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${localStorage.getItem("token")}`
                }
            });
            if (response.ok) {
                setFavoriler(prev => prev.filter(f => f.begenId !== begenId));
                alert("Favori not kaldırıldı!");
            } else {
                const errText = await response.text();
                console.error("Favori kaldırılamadı:", errText);
                alert("Favori kaldırılamadı!");
            }
        } catch (err) {
            console.error("Favori kaldırma isteği başarısız:", err);
            alert("Favori kaldırma isteği başarısız!");
        }
    };

    return (
        <div style={{ maxWidth: "1200px", margin: "0 auto" }}>
            {/* Header */}
            <div style={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
                backgroundColor: "#2196F3",
                color: "white",
                padding: "10px 20px",
                borderRadius: "4px",
                marginBottom: "20px"
            }}>
                <h2 style={{ margin: 0 }}>Favorilerim</h2>
                <div style={{ display: "flex", gap: "10px" }}>
                    <button onClick={goHome} style={buttonStyle("#ffffff", "#2196F3")}>Ana Sayfa</button>
                    <button onClick={handleLogout} style={buttonStyle("#f44336", "white")}>Çıkış</button>
                </div>
            </div>

            {loading ? (
                <p>Yükleniyor...</p>
            ) : favoriler.length === 0 ? (
                <p>Henüz favori notunuz yok.</p>
            ) : (
                <div style={{
                    display: "grid",
                    gridTemplateColumns: "repeat(auto-fill, minmax(250px, 1fr))",
                    gap: "20px"
                }}>
                    {favoriler.map(fav => (
                        <div key={fav.begenId} style={{
                            border: "1px solid #ddd",
                            borderRadius: "8px",
                            padding: "15px",
                            boxShadow: "0 2px 5px rgba(0,0,0,0.1)",
                            position: "relative"
                        }}>
                            {/* Clear ikon */}
                            <span
                                onClick={() => removeFavori(fav.begenId)}
                                style={{
                                    position: "absolute",
                                    top: 8,
                                    right: 8,
                                    cursor: "pointer",
                                    fontWeight: "bold",
                                    fontSize: "18px",
                                    color: "#f44336"
                                }}
                                title="Favoriden kaldır"
                            >
                                ×
                            </span>

                            <h4>{fav.dersNotu.dersNotAdi}</h4>
                            <p>{fav.dersNotu.dersNotIcerik}</p>
                            <p><strong>Fiyat:</strong> {fav.dersNotu.dersNotFiyat}</p>
                            <p><strong>Not Türü:</strong> {fav.dersNotu.notTuru?.notAdi || "-"}</p>
                            <p><strong>Kullanıcı:</strong> {fav.dersNotu.ogrenci?.username}</p>
                            <div style={{ display: "flex", gap: "10px", marginTop: "10px" }}>
                                <a href={`http://localhost:8085/${fav.dersNotu.dersNotPdf}`} target="_blank" rel="noopener noreferrer">PDF</a>
                                <a href={`http://localhost:8085/${fav.dersNotu.dersNotPdfOnizleme}`} target="_blank" rel="noopener noreferrer">Önizleme</a>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

const buttonStyle = (bgColor, color) => ({
    padding: "8px 16px",
    backgroundColor: bgColor,
    color: color,
    border: "none",
    borderRadius: "4px",
    cursor: "pointer"
});
