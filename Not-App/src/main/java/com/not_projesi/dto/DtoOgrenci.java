package com.not_projesi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoOgrenci {

    private String username;

    private String ogrenciAdi;

    private String ogrenciSoyadi;

    private String ogrenciEmail;

    private String ogrenciSifre;

    private DtoBolum dtoBolum;

}
