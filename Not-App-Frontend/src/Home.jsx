import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { FaRegHeart, FaHeart } from "react-icons/fa";

export default function Home() {
    const user = JSON.parse(localStorage.getItem("user"));
    const navigate = useNavigate();
    const [dersNotlari, setDersNotlari] = useState([]);
    const [loading, setLoading] = useState(true);
    const [likedNotes, setLikedNotes] = useState([]);

    useEffect(() => {
        document.body.style.margin = "0";
        document.body.style.padding = "0";
        document.body.style.height = "100vh";
        document.body.style.overflow = "hidden";
        document.documentElement.style.height = "100%";
    }, []);

    const goToProfileUpdate = () => navigate("/profile-update");
    const goToDersNotuEkle = () => navigate("/ders-notu-ekle");
    const goToDersEkle = () => navigate("/ders-ekle");
    const goToNotlarim = () => navigate("/notlarim");
    const handleLogout = () => {
        localStorage.removeItem("user");
        localStorage.removeItem("token");
        navigate("/login");
    };

    useEffect(() => {
        const fetchDersNotlari = async () => {
            try {
                const API_URL = process.env.REACT_APP_API_URL;
                const response = await fetch(`${API_URL}/ders-notu/list`, {
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`
                    }
                });
                const data = await response.json();
                setDersNotlari(data);

                const liked = data
                    .filter(n => n.begen && n.begen.length > 0)
                    .map(n => ({ dersNotId: n.dersNotId, begenId: n.begen[0].begenId }));
                setLikedNotes(liked);
            } catch (err) {
                console.error("Ders notları alınamadı:", err);
            } finally {
                setLoading(false);
            }
        };
        fetchDersNotlari();
    }, []);

    const handleLike = async (dersNotu) => {
        try {
            const API_URL = process.env.REACT_APP_API_URL;
            const response = await fetch(`${API_URL}/ekle/begen`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${localStorage.getItem("token")}`
                },
                body: JSON.stringify({
                    ogrenci: { username: user.username },
                    dersNotu: { dersNotId: dersNotu.dersNotId }
                })
            });

            if (response.ok) {
                const data = await response.json();
                setLikedNotes(prev => [...prev, { dersNotId: dersNotu.dersNotId, begenId: data.begenId }]);
                alert("Notu beğendiniz!");
            } else {
                const errText = await response.text();
                console.error("Beğeni eklenemedi:", errText);
                alert("Beğeni eklenemedi!");
            }
        } catch (err) {
            console.error("Beğeni isteği başarısız:", err);
            alert("Beğeni isteği başarısız oldu!");
        }
    };

    const handleUnlike = async (dersNotId, begenId) => {
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
                setLikedNotes(prev => prev.filter(b => b.dersNotId !== dersNotId));
                alert("Notu favorilerden kaldırdınız!");
            } else {
                const errText = await response.text();
                console.error("Beğeni silinemedi:", errText);
                alert("Beğeni silinemedi!");
            }
        } catch (err) {
            console.error("Beğeni silme isteği başarısız:", err);
            alert("Beğeni silme isteği başarısız oldu!");
        }
    };

    return (
        <div style={styles.container}>
            <div style={styles.innerContainer}>
                {/* Header */}
                <div style={styles.header}>
                    <div>
                        <h2 style={{ margin: 0 }}>Hoşgeldiniz, {user?.username || "Kullanıcı"}!</h2>
                        <p style={{ margin: "4px 0" }}>
                            {user?.ogrenciAdi} {user?.ogrenciSoyadi} | {user?.dtoBolum?.bolumAdi}
                        </p>
                    </div>
                    <div style={{ display: "flex", gap: "10px", flexWrap: "wrap" }}>
                        <button
                            id="btnDersEkle"   // <<< burayı ekle
                            onClick={goToDersEkle}
                            style={buttonStyle("#ffffff", "#2196F3")}
                        >
                            Ders Ekle
                        </button>
                        <button
                            id="btnDersNotuEkle"   // <<< burayı ekle
                            onClick={goToDersNotuEkle}
                            style={buttonStyle("#4CAF50", "white")}
                        >
                            Ders Notu Ekle
                        </button>

                        <button onClick={goToProfileUpdate} style={buttonStyle("#ffffff", "#2196F3")}>Profil Güncelle</button>
                        <button
                            id="btnNotlarim"
                            onClick={goToNotlarim}
                            style={buttonStyle("#FF9800", "white")}
                        >
                            Notlarım
                        </button>

                        <button onClick={() => navigate("/favorilerim")} style={buttonStyle("#FF4081", "white")}>Favorilerim</button>
                        <button onClick={handleLogout} style={buttonStyle("#f44336", "white")}>Çıkış</button>
                    </div>
                </div>

                <div style={styles.notesContainer}>
                    <h3>Tüm Ders Notları</h3>
                    {loading ? (
                        <p>Yükleniyor...</p>
                    ) : dersNotlari.length === 0 ? (
                        <p>Henüz ders notu yok.</p>
                    ) : (
                        <div style={styles.grid}>
                            {dersNotlari.map(not => {
                                const liked = likedNotes.find(b => b.dersNotId === not.dersNotId);
                                return (
                                    <div key={not.dersNotId} style={styles.card}>
                                        <span
                                            onClick={() => liked ? handleUnlike(not.dersNotId, liked.begenId) : handleLike(not)}
                                            style={{ position: "absolute", top: 8, right: 8, cursor: "pointer" }}
                                        >
                                            {liked ? <FaHeart color="red" size={20} /> : <FaRegHeart color="#f44336" size={20} />}
                                        </span>
                                        <h4>{not.dersNotAdi}</h4>
                                        <p>{not.dersNotIcerik}</p>
                                        <p><strong>Fiyat:</strong> {not.dersNotFiyat}</p>
                                        <p><strong>Not Türü:</strong> {not.notTuru?.notAdi || "-"}</p>
                                        <p><strong>Kullanıcı:</strong> {not.ogrenci?.username}</p>
                                        <div style={{ display: "flex", gap: "10px", marginTop: "10px" }}>
                                            <a href={`http://localhost:8085/${not.dersNotPdf}`} target="_blank" rel="noopener noreferrer">PDF</a>
                                            <a href={`http://localhost:8085/${not.dersNotPdfOnizleme}`} target="_blank" rel="noopener noreferrer">Önizleme</a>
                                        </div>
                                    </div>
                                );
                            })}
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

const styles = {
    container: {
        width: "100vw",
        height: "100vh",
        backgroundColor: "#ffffff",
        display: "flex",
        justifyContent: "center",
        alignItems: "flex-start",
        padding: "20px",
        boxSizing: "border-box",
    },
    innerContainer: {
        width: "100%",
        maxWidth: "1200px",
        display: "flex",
        flexDirection: "column",
        height: "100%",
    },
    header: {
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        backgroundColor: "#2196F3",
        color: "white",
        padding: "10px 20px",
        borderRadius: "4px",
        marginBottom: "20px",
        flexShrink: 0,
    },
    notesContainer: {
        flex: 1,
        overflowY: "auto",
        paddingRight: "20px",
    },
    grid: {
        display: "grid",
        gridTemplateColumns: "repeat(auto-fill, minmax(250px, 1fr))",
        gap: "20px",
    },
    card: {
        border: "1px solid #ddd",
        borderRadius: "8px",
        padding: "15px",
        boxShadow: "0 2px 5px rgba(0,0,0,0.1)",
        position: "relative",
    },
};



const buttonStyle = (bgColor, color) => ({
    padding: "8px 16px",
    backgroundColor: bgColor,
    color: color,
    border: "none",
    borderRadius: "4px",
    cursor: "pointer"
});
