package com.not_projesi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "satin_alinanlar")
public class SatinAlinanlar {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "username")
    private Ogrenci ogrenci;

    @Column(name = "urun_adi")
    private String urunAdi;

    @Column(name = "fiyat")
    private Double fiyat;

    @Column(name = "satin_alma_tarihi")
    private LocalDateTime satinAlmaTarihi;

    @Column(name = "odeme_durumu")
    private Boolean odemeDurumu;

    @ManyToOne
    @JoinColumn(name = "ders_not_id", referencedColumnName = "ders_not_id")
    private DersNotu dersNotu;

    @Column(name = "pdf_url")
    private String pdfUrl;

}