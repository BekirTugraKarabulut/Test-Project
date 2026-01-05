import React, { useState } from "react";

export default function DersNotuEkle() {
  const storedUser = JSON.parse(localStorage.getItem("user"));
  const username = storedUser?.username;

  const [form, setForm] = useState({
    dersNotAdi: "",
    dersNotIcerik: "",
    dersNotFiyat: "",
    notTuruId: "",
    pdfFile: null,
    pdfOnizlemeFile: null,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleFileChange = (e) => {
    const { name, files } = e.target;
    setForm({ ...form, [name]: files[0] });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const formData = new FormData();
      formData.append("username", username);
      formData.append("dersNotAdi", form.dersNotAdi);
      formData.append("dersNotIcerik", form.dersNotIcerik);
      formData.append("dersNotFiyat", form.dersNotFiyat);
      formData.append("notTuruId", form.notTuruId);
      formData.append("pdfFile", form.pdfFile);
      formData.append("pdfOnizlemeFile", form.pdfOnizlemeFile);

      const response = await fetch(
          `${API_URL}/ders-notu/ekle`, {
            method: "POST",
            body: formData,
            headers: {
          "Authorization": `Bearer ${localStorage.getItem("token")}`,
        },
      });

      if (response.ok) {
        alert("✅ Ders notu başarıyla eklendi!");
        setForm({
          dersNotAdi: "",
          dersNotIcerik: "",
          dersNotFiyat: "",
          notTuruId: "",
          pdfFile: null,
          pdfOnizlemeFile: null,
        });
      } else {
        const data = await response.json();
        alert("❌ Hata: " + (data?.message || "Ekleme başarısız"));
      }
    } catch (err) {
      console.error(err);
      alert("❌ Sunucuya bağlanırken hata oluştu!");
    }
  };

  return (
      <div style={styles.container}>
        <div style={styles.card}>
          <h2 style={styles.title}>Ders Notu Ekle</h2>
          <form onSubmit={handleSubmit} style={styles.form}>
            <input
                name="dersNotAdi"
                placeholder="Ders Not Adı"
                value={form.dersNotAdi}
                onChange={handleChange}
                required
                style={styles.input}
            />
            <textarea
                name="dersNotIcerik"
                placeholder="Ders Not İçerik"
                value={form.dersNotIcerik}
                onChange={handleChange}
                required
                style={{ ...styles.input, height: "100px", resize: "vertical" }}
            />
            <input
                name="dersNotFiyat"
                type="number"
                placeholder="Ders Not Fiyat"
                value={form.dersNotFiyat}
                onChange={handleChange}
                required
                style={styles.input}
            />
            <input
                name="notTuruId"
                type="number"
                placeholder="Not Türü ID"
                value={form.notTuruId}
                onChange={handleChange}
                required
                style={styles.input}
            />
            <label style={styles.fileLabel}>
              PDF Dosyası
              <input
                  name="pdfFile"
                  type="file"
                  accept=".pdf"
                  onChange={handleFileChange}
                  required
                  style={styles.fileInput}
              />
            </label>
            <label style={styles.fileLabel}>
              PDF Önizleme Dosyası
              <input
                  name="pdfOnizlemeFile"
                  type="file"
                  accept=".pdf"
                  onChange={handleFileChange}
                  required
                  style={styles.fileInput}
              />
            </label>
            <button type="submit" style={styles.button}>
              Ekle
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
    background: "#fff",
    padding: "30px 40px",
    borderRadius: "12px",
    boxShadow: "0 8px 25px rgba(0,0,0,0.15)",
    width: "100%",
    maxWidth: "600px",
  },
  title: {
    fontSize: "26px",
    fontWeight: "600",
    marginBottom: "25px",
    color: "#333",
    textAlign: "center",
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
  fileLabel: {
    display: "flex",
    flexDirection: "column",
    fontSize: "14px",
    fontWeight: "500",
    color: "#333",
  },
  fileInput: {
    marginTop: "5px",
  },
  button: {
    marginTop: "20px",
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
};
