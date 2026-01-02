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
@Table(name = "sepet")
public class Sepet {

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

    @Column(name = "ekleme_tarihi")
    private LocalDateTime eklemeTarihi;
}