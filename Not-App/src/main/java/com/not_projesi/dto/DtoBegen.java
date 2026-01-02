package com.not_projesi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoBegen {

    private Integer begenId;

    private DtoOgrenci ogrenci;

    private DtoDersNotu dersNotu;

}
