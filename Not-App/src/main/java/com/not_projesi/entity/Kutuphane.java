package com.not_projesi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kutuphane")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kutuphane {

    @Id
    @Column(name = "kutup_id")
    private Integer kutupId;

    @Column(name = "kutup_adi")
    private String kutupAdi;

}
