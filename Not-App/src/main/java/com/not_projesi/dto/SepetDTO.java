package com.not_projesi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SepetDTO {
    private Long id;
    private String ogrenciUsername;
    private String urunAdi;
    private Double fiyat;
    private LocalDateTime eklemeTarihi;
}