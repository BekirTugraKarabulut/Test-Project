package com.not_projesi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoFakulte {

    private Integer fakulteId;

    private String fakulteAdi;

    private DtoUniversite universite;

    private List<DtoBolum> bolumList;

}
