package com.not_projesi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoListele {

    private Integer listeleId;

    private Date listelenmeTarihi;

}
