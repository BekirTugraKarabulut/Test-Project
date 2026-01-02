package com.not_projesi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "fakulte")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fakulte {

    @Id
    @Column(name = "fakulte_id")
    private Integer fakulteId;

    @Column(name = "fakulte_adi")
    private String fakulteAdi;

    @ManyToOne
    @JoinColumn(name = "universite_id", referencedColumnName = "universite_id")
    private Universite universite;

    @OneToMany(mappedBy = "fakulte", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bolum> bolumList;

}
