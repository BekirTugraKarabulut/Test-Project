package com.not_projesi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "not_turu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotTuru {

    @Id
    @Column(name = "not_id")
    private Integer notId;

    @Column(name = "not_adi")
    private String notAdi;

    @OneToMany(mappedBy = "notTuru", cascade = CascadeType.ALL)
    private List<DersNotu> dersNotuList;

}
