package com.not_projesi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "begen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Begen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "begen_id")
    private Integer begenId;

    @ManyToOne
    @JoinColumn(name = "username" , referencedColumnName = "username")
    private Ogrenci ogrenci;

    @ManyToOne
    @JoinColumn(name = "ders_not_id" , referencedColumnName = "ders_not_id")
    private DersNotu dersNotu;

}
