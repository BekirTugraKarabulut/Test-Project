package com.not_projesi.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String username;

    private String ogrenciSifre;

    private String ogrenciAdi;

    private String ogrenciSoyadi;

    private String ogrenciEmail;

    private Integer bolumId;


}
