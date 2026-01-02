package com.not_projesi.service;

import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.dto.DtoOgrenciUI;

public interface ProfilService {

    public DtoOgrenci ogrenciBilgileriByUsername(String username);

    public DtoOgrenci guncelleOgrenciByUsername(String username , DtoOgrenciUI dtoOgrenciUI);

}
