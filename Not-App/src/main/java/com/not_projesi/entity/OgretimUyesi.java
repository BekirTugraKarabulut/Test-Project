package com.not_projesi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ogretim_uyesi")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OgretimUyesi {

    @Id
    @Column(name = "ogretim_uyesi_id")
    private Integer ogretimUyesiId;

    @Column(name = "ogretim_uyesi_adi")
    private String ogretimUyesiAdi;

    @Column(name = "ogretim_uyesi_soyadi")
    private String ogretimUyesiSoyadi;

    @Column(name = "ogretim_uyesi_email")
    private String ogretimUyesiEmail;

    @ManyToOne
    @JoinColumn(name = "ders_id" , referencedColumnName = "ders_id")
    private Ders ders;



}
