package com.not_projesi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoDersNotu {

    private Integer dersNotId;

    private String dersNotAdi;

    private String dersNotIcerik;

    private Integer dersNotFiyat;

    private String dersNotPdf;

    private String dersNotPdfOnizleme;

    private DtoOgrenci ogrenci;

    private DtoNotTuru notTuru;

}
