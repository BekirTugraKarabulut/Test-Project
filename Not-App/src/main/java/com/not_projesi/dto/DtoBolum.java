package com.not_projesi.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoBolum {

    private Integer bolumId;

    private String bolumAdi;

    private List<DtoOgrenci> ogrenci;

    private DtoFakulte fakulte;

}
