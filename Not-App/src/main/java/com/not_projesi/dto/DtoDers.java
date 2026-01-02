package com.not_projesi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoDers {

    private Integer dersId;

    private String dersAdi;

    private DtoOgrenci ogrenci;

    private List<DtoOgretimUyesi> ogretimUyesiList;

}
