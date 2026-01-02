package com.not_projesi.controller;

import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.dto.DtoOgrenciUI;

public interface IProfilController {

    public DtoOgrenci ogrenciBilgileriByUsername(String username);

    public DtoOgrenci guncelleOgrenciByUsername(String username , DtoOgrenciUI dtoOgrenciUI);


}
