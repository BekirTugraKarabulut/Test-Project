package com.not_projesi.service.impl;

import com.not_projesi.dto.DtoBolum;
import com.not_projesi.dto.DtoFakulte;
import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.dto.DtoOgrenciUI;
import com.not_projesi.entity.Bolum;
import com.not_projesi.entity.Fakulte;
import com.not_projesi.entity.Ogrenci;
import com.not_projesi.repository.ProfilRepository;
import com.not_projesi.service.ProfilService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfilServiceImpl implements ProfilService {

    @Autowired
    private ProfilRepository profilRepository;

    @Override
    public DtoOgrenci ogrenciBilgileriByUsername(String username) {

        DtoOgrenci dtoOgrenci = new DtoOgrenci();
        Ogrenci ogrenci = profilRepository.findByUsername(username);

        DtoBolum dtoBolum = new DtoBolum();
        Bolum bolum = ogrenci.getBolumList();

        DtoFakulte dtoFakulte = new DtoFakulte();
        Fakulte fakulte = bolum.getFakulte();

        ogrenci.setUsername(ogrenci.getUsername());
        ogrenci.setOgrenciAdi(ogrenci.getOgrenciAdi());
        ogrenci.setOgrenciSoyadi(ogrenci.getOgrenciSoyadi());
        ogrenci.setOgrenciEmail(ogrenci.getOgrenciEmail());
        ogrenci.setOgrenciSifre(ogrenci.getOgrenciSifre());
        ogrenci.getBolumList().setBolumAdi(bolum.getBolumAdi());

        BeanUtils.copyProperties(fakulte,dtoFakulte);

        bolum.setBolumAdi(bolum.getBolumAdi());
        bolum.getFakulte().setFakulteAdi(fakulte.getFakulteAdi());
        BeanUtils.copyProperties(bolum, dtoBolum);

        BeanUtils.copyProperties(ogrenci, dtoOgrenci);

        dtoBolum.setFakulte(dtoFakulte);
        dtoOgrenci.setDtoBolum(dtoBolum);

        return dtoOgrenci;
    }

    @Override
    public DtoOgrenci guncelleOgrenciByUsername(String username, DtoOgrenciUI dtoOgrenciUI) {

        DtoOgrenci dtoOgrenci = new DtoOgrenci();
        Ogrenci ogrenci = profilRepository.findByUsername(username);

        if(ogrenci == null){
            return null;
        }

        DtoBolum dtoBolum = new DtoBolum();
        Bolum bolum = ogrenci.getBolumList();

        DtoFakulte dtoFakulte = new DtoFakulte();
        Fakulte fakulte = bolum.getFakulte();

        if (ogrenci == null) {
            return null;
        }

        ogrenci.setUsername(ogrenci.getUsername());
        ogrenci.setOgrenciAdi(dtoOgrenciUI.getOgrenciAdi());
        ogrenci.setOgrenciSoyadi(dtoOgrenciUI.getOgrenciSoyadi());
        ogrenci.getBolumList().setBolumAdi(dtoOgrenciUI.getDtoBolum().getBolumAdi());
        ogrenci.getBolumList().getFakulte().setFakulteAdi(dtoOgrenciUI.getDtoBolum().getFakulte().getFakulteAdi());

        bolum.setBolumAdi(ogrenci.getBolumList().getBolumAdi());
        bolum.getFakulte().setFakulteAdi(ogrenci.getBolumList().getFakulte().getFakulteAdi());

        Ogrenci dbOgrenci = profilRepository.save(ogrenci);

        BeanUtils.copyProperties(bolum, dtoBolum);
        BeanUtils.copyProperties(fakulte, dtoFakulte);

        BeanUtils.copyProperties(dbOgrenci, dtoOgrenci);

        dtoBolum.setFakulte(dtoFakulte);
        dtoOgrenci.setDtoBolum(dtoBolum);

        return dtoOgrenci;
    }


}
