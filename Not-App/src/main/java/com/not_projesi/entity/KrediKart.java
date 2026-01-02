package com.not_projesi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kredi_kart")
public class KrediKart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ogrenci_id")
    private Ogrenci ogrenci;

    @Column(name = "kart_no")
    private String kartNo;

    @Column(name = "son_kullanma_tarihi")
    private String sonKullanmaTarihi;

    @Column(name = "cvv")
    private String cvv;

    @Column(name = "kart_sahibi")
    private String kartSahibi;
} 