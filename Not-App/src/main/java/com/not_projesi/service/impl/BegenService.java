package com.not_projesi.service.impl;

import com.not_projesi.dto.DtoBegen;
import com.not_projesi.dto.DtoBegenUI;
import com.not_projesi.dto.DtoDersNotu;
import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.entity.Begen;
import com.not_projesi.entity.DersNotu;
import com.not_projesi.entity.Ogrenci;
import com.not_projesi.exception.BaseException;
import com.not_projesi.exception.ErrorMesaj;
import com.not_projesi.exception.MesajTipi;
import com.not_projesi.repository.BegenRepository;
import com.not_projesi.repository.DersNotuRepository;
import com.not_projesi.repository.OgrenciRepository;
import com.not_projesi.service.IBegenService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BegenService implements IBegenService {

    @Autowired
    private BegenRepository begenRepository;
    @Autowired
    private OgrenciRepository ogrenciRepository;
    @Autowired
    private DersNotuRepository dersNotuRepository;

    @Override
    public List<DtoBegen> findAll() {
        List<DtoBegen> dtoBegenList = new ArrayList<>();
        List<Begen> begenList = begenRepository.findAll();

        if (begenList.isEmpty()) {
            return null;
        }

        for (Begen begen : begenList) {
            DtoBegen dtoBegen = new DtoBegen();
            BeanUtils.copyProperties(begen, dtoBegen);

            if (begen.getOgrenci() != null) {
                DtoOgrenci dtoOgrenci = new DtoOgrenci();
                BeanUtils.copyProperties(begen.getOgrenci(), dtoOgrenci);
                dtoBegen.setOgrenci(dtoOgrenci);
            }

            dtoBegenList.add(dtoBegen);
        }

        return dtoBegenList;
    }

    @Override
    public DtoBegen save(DtoBegenUI dtoBegenUI) {

        String username = dtoBegenUI.getOgrenci().getUsername();
        Optional<Ogrenci> ogrenciOpt = ogrenciRepository.findByUsername(username);

        if(ogrenciOpt.isEmpty()){
            throw new BaseException(new ErrorMesaj(MesajTipi.ARANAN_OGRENCI, "Öğrenci Bulunamadi: " + username));
        }

        Ogrenci ogrenci = ogrenciOpt.get();

        Integer dersNotId = dtoBegenUI.getDersNotu().getDersNotId();
        Optional<DersNotu> dersNotuOpt = dersNotuRepository.findById(dersNotId);

        if(dersNotuOpt.isEmpty()){
            throw new BaseException(new ErrorMesaj(MesajTipi.DERS_NOTU_YOKTUR, "Ders notu bulunamadı: ID=" + dersNotId));
        }

        DersNotu dersNotu = dersNotuOpt.get();

        Begen begen = new Begen();
        begen.setOgrenci(ogrenci);
        begen.setDersNotu(dersNotu);

        Begen dbBegen = begenRepository.save(begen);

        DtoBegen dtoBegen = new DtoBegen();
        BeanUtils.copyProperties(dbBegen, dtoBegen);

        DtoOgrenci dtoOgrenci = new DtoOgrenci();
        BeanUtils.copyProperties(ogrenci, dtoOgrenci);

        DtoDersNotu dtoDersNotu = new DtoDersNotu();
        BeanUtils.copyProperties(dersNotu, dtoDersNotu);

        dtoBegen.setOgrenci(dtoOgrenci);
        dtoBegen.setDersNotu(dtoDersNotu);

        return dtoBegen;
    }

    @Override
    public void deleteById(Integer begenId) {
        begenRepository.deleteById(begenId);
    }

    @Override
    public List<DtoBegen> getBegenByUsername(String username) {

        List<DtoBegen> dtoBegenList = new ArrayList<>();
        List<Begen> begenList = begenRepository.findAll();

        if (begenList.isEmpty()) {
            return null;
        }

        for (Begen begeniler : begenList) {
            if(begeniler.getOgrenci().getUsername().equals(username)){
                DtoBegen dtoBegen = new DtoBegen();
                BeanUtils.copyProperties(begeniler, dtoBegen);

                DtoOgrenci dtoOgrenci = new DtoOgrenci();
                BeanUtils.copyProperties(begeniler.getOgrenci(), dtoOgrenci);
                dtoBegen.setOgrenci(dtoOgrenci);

                DtoDersNotu dtoDersNotu = new DtoDersNotu();
                BeanUtils.copyProperties(begeniler.getDersNotu(), dtoDersNotu);
                dtoBegen.setDersNotu(dtoDersNotu);

                dtoBegenList.add(dtoBegen);
            }
        }
        return dtoBegenList;
    }

    @Override
    public DtoBegen update(DtoBegen dtoBegen) {
        Optional<Begen> optionalBegen = begenRepository.findById(dtoBegen.getBegenId());

        if (optionalBegen.isEmpty()) {
            return null;
        }

        Begen existingBegen = optionalBegen.get();
        BeanUtils.copyProperties(dtoBegen, existingBegen, "begenId");

        if (dtoBegen.getOgrenci() != null) {
            Ogrenci ogrenci = new Ogrenci();
            BeanUtils.copyProperties(dtoBegen.getOgrenci(), ogrenci);
            existingBegen.setOgrenci(ogrenci);
        }

        Begen updatedBegen = begenRepository.save(existingBegen);
        DtoBegen updatedDtoBegen = new DtoBegen();
        BeanUtils.copyProperties(updatedBegen, updatedDtoBegen);
        return updatedDtoBegen;
    }
}