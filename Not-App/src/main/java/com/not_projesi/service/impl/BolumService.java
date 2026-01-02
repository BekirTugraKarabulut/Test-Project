package com.not_projesi.service.impl;

import com.not_projesi.dto.DtoBolum;
import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.entity.Bolum;
import com.not_projesi.entity.Ogrenci;
import com.not_projesi.repository.BolumRepository;
import com.not_projesi.service.IBolumService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BolumService implements IBolumService {

    @Autowired
    private BolumRepository bolumRepository;

    @Override
    public List<DtoBolum> findAll() {
        List<DtoBolum> dtoBolumList = new ArrayList<>();
        List<Bolum> bolumList = bolumRepository.findAll();

        if (bolumList.isEmpty()) {
            return null;
        }

        for (Bolum bolum : bolumList) {
            DtoBolum dtoBolum = new DtoBolum();
            BeanUtils.copyProperties(bolum, dtoBolum);
            
            if (bolum.getOgrenci() != null) {
                List<DtoOgrenci> dtoOgrenciList = new ArrayList<>();
                for (Ogrenci ogrenci : bolum.getOgrenci()) {
                    DtoOgrenci dtoOgrenci = new DtoOgrenci();
                    BeanUtils.copyProperties(ogrenci, dtoOgrenci);
                    dtoOgrenciList.add(dtoOgrenci);
                }
                dtoBolum.setOgrenci(dtoOgrenciList);
            }
            
            dtoBolumList.add(dtoBolum);
        }

        return dtoBolumList;
    }

    @Override
    public DtoBolum findById(Integer bolumId) {
        Optional<Bolum> optionalBolum = bolumRepository.findById(bolumId);
        
        if (optionalBolum.isEmpty()) {
            return null;
        }

        Bolum bolum = optionalBolum.get();
        DtoBolum dtoBolum = new DtoBolum();
        BeanUtils.copyProperties(bolum, dtoBolum);

        if (bolum.getOgrenci() != null) {
            List<DtoOgrenci> dtoOgrenciList = new ArrayList<>();
            for (Ogrenci ogrenci : bolum.getOgrenci()) {
                DtoOgrenci dtoOgrenci = new DtoOgrenci();
                BeanUtils.copyProperties(ogrenci, dtoOgrenci);
                dtoOgrenciList.add(dtoOgrenci);
            }
            dtoBolum.setOgrenci(dtoOgrenciList);
        }

        return dtoBolum;
    }

    @Override
    public DtoBolum save(DtoBolum dtoBolum) {
        Bolum bolum = new Bolum();
        BeanUtils.copyProperties(dtoBolum, bolum);

        if (dtoBolum.getOgrenci() != null) {
            List<Ogrenci> ogrenciList = new ArrayList<>();
            for (DtoOgrenci dtoOgrenci : dtoBolum.getOgrenci()) {
                Ogrenci ogrenci = new Ogrenci();
                BeanUtils.copyProperties(dtoOgrenci, ogrenci);
                ogrenciList.add(ogrenci);
            }
            bolum.setOgrenci(ogrenciList);
        }

        Bolum savedBolum = bolumRepository.save(bolum);
        DtoBolum savedDtoBolum = new DtoBolum();
        BeanUtils.copyProperties(savedBolum, savedDtoBolum);
        return savedDtoBolum;
    }

    @Override
    public void deleteById(Integer bolumId) {
        bolumRepository.deleteById(bolumId);
    }

    @Override
    public DtoBolum update(DtoBolum dtoBolum) {
        Optional<Bolum> optionalBolum = bolumRepository.findById(dtoBolum.getBolumId());
        
        if (optionalBolum.isEmpty()) {
            return null;
        }

        Bolum existingBolum = optionalBolum.get();
        BeanUtils.copyProperties(dtoBolum, existingBolum, "bolumId");

        if (dtoBolum.getOgrenci() != null) {
            List<Ogrenci> ogrenciList = new ArrayList<>();
            for (DtoOgrenci dtoOgrenci : dtoBolum.getOgrenci()) {
                Ogrenci ogrenci = new Ogrenci();
                BeanUtils.copyProperties(dtoOgrenci, ogrenci);
                ogrenciList.add(ogrenci);
            }
            existingBolum.setOgrenci(ogrenciList);
        }

        Bolum updatedBolum = bolumRepository.save(existingBolum);
        DtoBolum updatedDtoBolum = new DtoBolum();
        BeanUtils.copyProperties(updatedBolum, updatedDtoBolum);
        return updatedDtoBolum;
    }
} 