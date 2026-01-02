package com.not_projesi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "listele")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Listele {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listele_id")
    private Integer listeleId;

    @Column(name = "listelenme_tarihi")
    private Date listelenmeTarihi;

}
