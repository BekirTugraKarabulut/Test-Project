package com.not_projesi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "ders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ders {

    @Id
    @Column(name = "ders_id")
    private Integer dersId;

    @Column(name = "ders_adi")
    private String dersAdi;

    @ManyToOne
    @JoinColumn(name = "ogrenci_id" , referencedColumnName = "username")
    private Ogrenci ogrenci;

    @OneToMany(mappedBy = "ders" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OgretimUyesi>  ogretimUyesiList;
}
