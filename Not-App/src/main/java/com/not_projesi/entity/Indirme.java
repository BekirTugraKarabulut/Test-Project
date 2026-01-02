package com.not_projesi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "indirme")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Indirme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indir_id")
    private Long id;

    @Column(name = "indirme_sayisi")
    private Integer indirmeSayisi;

}
