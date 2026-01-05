import { useState } from "react";

function App() {
    const [isLogin, setIsLogin] = useState(false);

    const [regUsername, setRegUsername] = useState("");
    const [ogrenciSifre, setOgrenciSifre] = useState("");
    const [ogrenciAdi, setOgrenciAdi] = useState("");
    const [ogrenciSoyadi, setOgrenciSoyadi] = useState("");
    const [ogrenciEmail, setOgrenciEmail] = useState("");
    const [bolumId, setBolumId] = useState("");
    const [regMessage, setRegMessage] = useState("");

    const [loginUsername, setLoginUsername] = useState("");
    const [loginPassword, setLoginPassword] = useState("");
    const [loginMessage, setLoginMessage] = useState("");

    const handleRegister = async () => {
        try {
            const res = await fetch("http://localhost:8085/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    username: regUsername,
                    ogrenciSifre,
                    ogrenciAdi,
                    ogrenciSoyadi,
                    ogrenciEmail,
                    bolumId: parseInt(bolumId),
                }),
            });

            if (res.ok) setRegMessage("Kayıt başarılı! Şimdi giriş yapabilirsiniz.");
            else setRegMessage("Hata: " + (await res.text()));
        } catch {
            setRegMessage("Backend erişilemiyor.");
        }
    };

    const handleLogin = async () => {
        try {
            const res = await fetch("http://localhost:8085/authenticate", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username: loginUsername, ogrenciSifre: loginPassword }),
            });

            if (res.ok) {
                const data = await res.json();
                setLoginMessage("Giriş başarılı! Token: " + data.token);
            } else setLoginMessage("Hata: " + (await res.text()));
        } catch {
            setLoginMessage("Backend erişilemiyor.");
        }
    };

    return (
        <div style={{ padding: "20px", fontFamily: "Arial", maxWidth: "500px", margin: "0 auto" }}>
            <h1>Not Projesi Frontend</h1>

            {isLogin ? (
                // LOGIN FORM
                <div>
                    <h2>Login</h2>
                    <input
                        placeholder="Kullanıcı adı"
                        value={loginUsername}
                        onChange={(e) => setLoginUsername(e.target.value)}
                        style={{ display: "block", marginBottom: "10px" }}
                    />
                    <input
                        placeholder="Şifre"
                        type="password"
                        value={loginPassword}
                        onChange={(e) => setLoginPassword(e.target.value)}
                        style={{ display: "block", marginBottom: "10px" }}
                    />
                    <button onClick={handleLogin} style={{ marginBottom: "10px" }}>
                        Login
                    </button>
                    <p>{loginMessage}</p>
                    <button onClick={() => setIsLogin(false)}>Register’e geç</button>
                </div>
            ) : (
                // REGISTER FORM
                <div>
                    <h2>Register</h2>
                    <input
                        placeholder="Kullanıcı adı"
                        value={regUsername}
                        onChange={(e) => setRegUsername(e.target.value)}
                        style={{ display: "block", marginBottom: "10px" }}
                    />
                    <input
                        placeholder="Şifre"
                        type="password"
                        value={ogrenciSifre}
                        onChange={(e) => setOgrenciSifre(e.target.value)}
                        style={{ display: "block", marginBottom: "10px" }}
                    />
                    <input
                        placeholder="Adınız"
                        value={ogrenciAdi}
                        onChange={(e) => setOgrenciAdi(e.target.value)}
                        style={{ display: "block", marginBottom: "10px" }}
                    />
                    <input
                        placeholder="Soyadınız"
                        value={ogrenciSoyadi}
                        onChange={(e) => setOgrenciSoyadi(e.target.value)}
                        style={{ display: "block", marginBottom: "10px" }}
                    />
                    <input
                        placeholder="Email"
                        value={ogrenciEmail}
                        onChange={(e) => setOgrenciEmail(e.target.value)}
                        style={{ display: "block", marginBottom: "10px" }}
                    />
                    <input
                        placeholder="Bölüm ID"
                        type="number"
                        value={bolumId}
                        onChange={(e) => setBolumId(e.target.value)}
                        style={{ display: "block", marginBottom: "10px" }}
                    />
                    <button onClick={handleRegister} style={{ marginBottom: "10px" }}>
                        Register
                    </button>
                    <p>{regMessage}</p>
                    <button onClick={() => setIsLogin(true)}>Login’e geç</button>
                </div>
            )}
        </div>
    );
}

export default App;
