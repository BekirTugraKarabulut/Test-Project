package com.not_projesi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KrediKartDTO {

    private Long id;

    private String ogrenciUsername;

    private String kartNo;

    private String sonKullanmaTarihi;

    private String cvv;

    private String kartSahibi;

} 