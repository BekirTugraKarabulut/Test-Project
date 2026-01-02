package com.not_projesi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoOgrenciUI {

    private String ogrenciAdi;

    private String ogrenciSoyadi;

    private String ogrenciEmail;

    private DtoBolum dtoBolum;

}
