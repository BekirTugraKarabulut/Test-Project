package com.not_projesi.controller.impl;

import com.not_projesi.controller.IProfilController;
import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.dto.DtoOgrenciUI;
import com.not_projesi.service.ProfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "rest")
public class ProfilControllerImpl implements IProfilController {

    @Autowired
    private ProfilService profilService;

    @Override
    @GetMapping(path = "/profil-bilgi/{username}")
    public DtoOgrenci ogrenciBilgileriByUsername(@PathVariable(name = "username" , required = true) String username) {
        return profilService.ogrenciBilgileriByUsername(username);
    }

    @PutMapping(path = "/guncelle/profil-bilgi/{username}")
    @Override
    public DtoOgrenci guncelleOgrenciByUsername(@PathVariable(name = "username" , required = true) String username,@RequestBody DtoOgrenciUI dtoOgrenciUI) {
        return profilService.guncelleOgrenciByUsername(username , dtoOgrenciUI);
    }
}
