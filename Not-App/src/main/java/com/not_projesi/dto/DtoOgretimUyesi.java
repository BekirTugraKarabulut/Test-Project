package com.not_projesi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoOgretimUyesi {

    private Integer ogretimUyesiId;

    private String ogretimUyesiAdi;

    private String ogretimUyesiSoyadi;

    private String ogretimUyesiEmail;

}
