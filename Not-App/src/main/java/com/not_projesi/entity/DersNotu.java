package com.not_projesi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "ders_notu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DersNotu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ders_not_id")
    private Integer dersNotId;

    @Column(name = "ders_not_adi")
    private String dersNotAdi;

    @Column(name = "ders_not_icerik")
    private String dersNotIcerik;

    @Column(name = "ders_not_fiyat")
    private Integer dersNotFiyat;

    @Column(name = "ders_not_pdf")
    private String dersNotPdf;

    @Column(name = "ders_not_pdf_onizleme")
    private String dersNotPdfOnizleme;

    @ManyToOne
    @JoinColumn(name = "username" , referencedColumnName = "username")
    private Ogrenci ogrenci;

    @OneToMany(mappedBy = "dersNotu" , cascade = CascadeType.ALL)
    private List<Begen> begenList;

    @ManyToOne
    @JoinColumn(name = "not_turu_id" , referencedColumnName = "not_id")
    private NotTuru notTuru;

}
