package com.not_projesi.service.impl;

import com.not_projesi.dto.DtoBolum;
import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.entity.Bolum;
import com.not_projesi.entity.Ogrenci;
import com.not_projesi.repository.OgrenciRepository;
import com.not_projesi.service.IOgrenciService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OgrenciService implements IOgrenciService {

    @Autowired
    private OgrenciRepository ogrenciRepository;

    @Override
    public List<DtoOgrenci> findAll() {

        List<DtoOgrenci> dtoOgrenci = new ArrayList<>();
        List<Ogrenci> ogrenciList = ogrenciRepository.findAll();

        if(ogrenciList.isEmpty()){
            return null;
        }

        for(Ogrenci ogrenci : ogrenciList){
            DtoOgrenci dto = new DtoOgrenci();
            BeanUtils.copyProperties(ogrenci,dto);
            dtoOgrenci.add(dto);
        }

        return dtoOgrenci;
    }

    @Override
    public DtoOgrenci findDtoOgrenciByUsername(String username) {

        DtoOgrenci dtoOgrenci = new DtoOgrenci();
        Ogrenci ogrenci = new Ogrenci();

        Optional<Ogrenci> optional = ogrenciRepository.findByUsername(username);

        if(optional.isEmpty()){
            return null;
        }

        Ogrenci dbOgrenci = optional.get();
        Bolum bolum = optional.get().getBolumList();

        DtoBolum dtoBolum = new DtoBolum();
        BeanUtils.copyProperties(dbOgrenci,dtoOgrenci);

        BeanUtils.copyProperties(bolum,dtoBolum);
        dtoOgrenci.setDtoBolum(dtoBolum);

        return dtoOgrenci;
    }


}
