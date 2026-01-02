package com.not_projesi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "universite")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Universite {

    @Id
    @Column(name = "universite_id")
    private Integer universiteId;

    @Column(name = "universite_adi")
    private String universiteAdi;

    @OneToMany(mappedBy = "universite",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Fakulte> fakulte;

}


