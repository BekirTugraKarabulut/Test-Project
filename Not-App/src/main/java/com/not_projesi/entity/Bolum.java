package com.not_projesi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "bolum")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bolum {

    @Id
    @Column(name = "bolum_id")
    private Integer bolumId;

    @Column(name = "bolum_adi")
    private String bolumAdi;

    @OneToMany(mappedBy = "bolumList" , cascade = CascadeType.ALL)
    private List<Ogrenci> ogrenci;

    @ManyToOne
    @JoinColumn(name = "fakulte_id" , referencedColumnName = "fakulte_id")
    private Fakulte fakulte;

}
