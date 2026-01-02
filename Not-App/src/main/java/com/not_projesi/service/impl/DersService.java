package com.not_projesi.service.impl;

import com.not_projesi.dto.DtoDers;
import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.dto.DtoOgretimUyesi;
import com.not_projesi.entity.Ders;
import com.not_projesi.entity.Ogrenci;
import com.not_projesi.entity.OgretimUyesi;
import com.not_projesi.repository.DersRepository;
import com.not_projesi.service.IDersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DersService implements IDersService {

    @Autowired
    private DersRepository dersRepository;

    @Override
    public List<DtoDers> findAll() {
        List<DtoDers> dtoDersList = new ArrayList<>();
        List<Ders> dersList = dersRepository.findAll();

        if (dersList.isEmpty()) {
            return null;
        }

        for (Ders ders : dersList) {
            DtoDers dtoDers = new DtoDers();
            BeanUtils.copyProperties(ders, dtoDers);
            
            if (ders.getOgrenci() != null) {
                DtoOgrenci dtoOgrenci = new DtoOgrenci();
                BeanUtils.copyProperties(ders.getOgrenci(), dtoOgrenci);
                dtoDers.setOgrenci(dtoOgrenci);
            }
            
            if (ders.getOgretimUyesiList() != null) {
                List<DtoOgretimUyesi> dtoOgretimUyesiList = new ArrayList<>();
                for (OgretimUyesi ogretimUyesi : ders.getOgretimUyesiList()) {
                    DtoOgretimUyesi dtoOgretimUyesi = new DtoOgretimUyesi();
                    BeanUtils.copyProperties(ogretimUyesi, dtoOgretimUyesi);
                    dtoOgretimUyesiList.add(dtoOgretimUyesi);
                }
                dtoDers.setOgretimUyesiList(dtoOgretimUyesiList);
            }
            
            dtoDersList.add(dtoDers);
        }

        return dtoDersList;
    }

    @Override
    public DtoDers findById(Integer dersId) {
        Optional<Ders> optionalDers = dersRepository.findById(dersId);
        
        if (optionalDers.isEmpty()) {
            return null;
        }

        Ders ders = optionalDers.get();
        DtoDers dtoDers = new DtoDers();
        BeanUtils.copyProperties(ders, dtoDers);

        if (ders.getOgrenci() != null) {
            DtoOgrenci dtoOgrenci = new DtoOgrenci();
            BeanUtils.copyProperties(ders.getOgrenci(), dtoOgrenci);
            dtoDers.setOgrenci(dtoOgrenci);
        }

        if (ders.getOgretimUyesiList() != null) {
            List<DtoOgretimUyesi> dtoOgretimUyesiList = new ArrayList<>();
            for (OgretimUyesi ogretimUyesi : ders.getOgretimUyesiList()) {
                DtoOgretimUyesi dtoOgretimUyesi = new DtoOgretimUyesi();
                BeanUtils.copyProperties(ogretimUyesi, dtoOgretimUyesi);
                dtoOgretimUyesiList.add(dtoOgretimUyesi);
            }
            dtoDers.setOgretimUyesiList(dtoOgretimUyesiList);
        }

        return dtoDers;
    }

    @Override
    public DtoDers save(DtoDers dtoDers) {
        Ders ders = new Ders();
        BeanUtils.copyProperties(dtoDers, ders);

        if (dtoDers.getOgrenci() != null) {
            Ogrenci ogrenci = new Ogrenci();
            BeanUtils.copyProperties(dtoDers.getOgrenci(), ogrenci);
            ders.setOgrenci(ogrenci);
        }

        if (dtoDers.getOgretimUyesiList() != null) {
            List<OgretimUyesi> ogretimUyesiList = new ArrayList<>();
            for (DtoOgretimUyesi dtoOgretimUyesi : dtoDers.getOgretimUyesiList()) {
                OgretimUyesi ogretimUyesi = new OgretimUyesi();
                BeanUtils.copyProperties(dtoOgretimUyesi, ogretimUyesi);
                ogretimUyesiList.add(ogretimUyesi);
            }
            ders.setOgretimUyesiList(ogretimUyesiList);
        }

        Ders savedDers = dersRepository.save(ders);
        DtoDers savedDtoDers = new DtoDers();
        BeanUtils.copyProperties(savedDers, savedDtoDers);
        return savedDtoDers;
    }

    @Override
    public void deleteById(Integer dersId) {
        dersRepository.deleteById(dersId);
    }

    @Override
    public DtoDers update(DtoDers dtoDers) {
        Optional<Ders> optionalDers = dersRepository.findById(dtoDers.getDersId());
        
        if (optionalDers.isEmpty()) {
            return null;
        }

        Ders existingDers = optionalDers.get();
        BeanUtils.copyProperties(dtoDers, existingDers, "dersId");

        if (dtoDers.getOgrenci() != null) {
            Ogrenci ogrenci = new Ogrenci();
            BeanUtils.copyProperties(dtoDers.getOgrenci(), ogrenci);
            existingDers.setOgrenci(ogrenci);
        }

        if (dtoDers.getOgretimUyesiList() != null) {
            List<OgretimUyesi> ogretimUyesiList = new ArrayList<>();
            for (DtoOgretimUyesi dtoOgretimUyesi : dtoDers.getOgretimUyesiList()) {
                OgretimUyesi ogretimUyesi = new OgretimUyesi();
                BeanUtils.copyProperties(dtoOgretimUyesi, ogretimUyesi);
                ogretimUyesiList.add(ogretimUyesi);
            }
            existingDers.setOgretimUyesiList(ogretimUyesiList);
        }

        Ders updatedDers = dersRepository.save(existingDers);
        DtoDers updatedDtoDers = new DtoDers();
        BeanUtils.copyProperties(updatedDers, updatedDtoDers);
        return updatedDtoDers;
    }
} 