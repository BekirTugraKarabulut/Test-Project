package com.not_projesi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoNotTuru {

    private Integer notId;

    private String notAdi;

    private DtoDersNotu dersNotu;

}
