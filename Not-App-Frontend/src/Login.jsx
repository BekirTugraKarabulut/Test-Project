import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function Login() {
    const navigate = useNavigate();

    const [form, setForm] = useState({
        username: "",
        ogrenciSifre: "",
        ogrenciAdi: "",
        ogrenciSoyadi: "",
        ogrenciEmail: "",
        bolumId: "",
    });

    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        // Sayfa yüklendiğinde body ve html tam ekran olsun
        document.body.style.margin = "0";
        document.body.style.padding = "0";
        document.body.style.height = "100vh";
        document.body.style.overflow = "hidden";
        document.documentElement.style.height = "100%";
    }, []);

    const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

    const handleLogin = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError("");

        try {
            const res = await fetch("http://localhost:8085/authenticate", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(form),
            });

            if (!res.ok) {
                setError("Kullanıcı adı veya şifre hatalı");
                return;
            }

            const authData = await res.json();
            localStorage.setItem("token", authData.token);

            const userRes = await fetch(
                `http://localhost:8085/rest/list/${form.username}`,
                { headers: { "Authorization": `Bearer ${authData.token}` } }
            );

            if (!userRes.ok) {
                alert("Kullanıcı bilgileri alınamadı!");
                return;
            }

            const userData = await userRes.json();
            localStorage.setItem("user", JSON.stringify(userData));

            navigate("/home");
        } catch (err) {
            console.error(err);
            setError("Sunucu hatası: " + (err.message || "Bilinmeyen hata"));
        } finally {
            setLoading(false);
        }
    };

    return (
        <div style={styles.container}>
            <div style={styles.card}>
                <h2 style={styles.title}>Giriş Yap</h2>
                <form onSubmit={handleLogin} style={styles.form}>
                    {[
                        { name: "username", placeholder: "Kullanıcı adı" },
                        { name: "ogrenciSifre", placeholder: "Şifre", type: "password" },
                        { name: "ogrenciAdi", placeholder: "Ad" },
                        { name: "ogrenciSoyadi", placeholder: "Soyad" },
                        { name: "ogrenciEmail", placeholder: "Email", type: "email" },
                        { name: "bolumId", placeholder: "Bölüm ID", type: "number" },
                    ].map((field) => (
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

                    <button type="submit" disabled={loading} style={styles.button}>
                        {loading ? "Giriş yapılıyor..." : "Giriş Yap"}
                    </button>

                    {error && <p id="loginError" style={styles.error}>{error}</p>}
                </form>

                {/* Kayıt Ol Butonu */}
                <button
                    onClick={() => navigate("/register")}
                    style={styles.registerButton}
                >
                    Kayıt Ol
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
        background: "linear-gradient(135deg, #4CAF50 0%, #2196F3 100%)",
    },
    card: {
        background: "#fff",
        padding: "30px 40px",
        borderRadius: "12px",
        boxShadow: "0 10px 25px rgba(0,0,0,0.2)",
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
        padding: "12px 15px",
        borderRadius: "8px",
        border: "1px solid #ccc",
        fontSize: "16px",
        outline: "none",
        transition: "all 0.2s ease",
    },
    button: {
        padding: "12px",
        borderRadius: "8px",
        border: "none",
        background: "#2196F3",
        color: "white",
        fontSize: "16px",
        fontWeight: "600",
        cursor: "pointer",
        transition: "all 0.2s ease",
    },
    error: {
        color: "#f44336",
        marginTop: "10px",
        fontWeight: "500",
    },
    registerButton: {
        marginTop: "15px",
        padding: "12px",
        borderRadius: "8px",
        border: "none",
        background: "#4CAF50",
        color: "white",
        fontSize: "16px",
        fontWeight: "600",
        cursor: "pointer",
        transition: "all 0.2s ease",
    },
};
