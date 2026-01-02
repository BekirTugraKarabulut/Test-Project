package com.not_projesi.service.impl;

import com.not_projesi.dto.DtoDersNotu;
import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.dto.DtoNotTuru;
import com.not_projesi.entity.DersNotu;
import com.not_projesi.entity.NotTuru;
import com.not_projesi.entity.Ogrenci;
import com.not_projesi.repository.DersNotuRepository;
import com.not_projesi.repository.NotTuruRepository;
import com.not_projesi.repository.OgrenciRepository;
import com.not_projesi.service.IDersNotuService;
import com.not_projesi.service.IFileStorageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DersNotuService implements IDersNotuService {

    @Autowired
    private DersNotuRepository dersNotuRepository;

    @Autowired
    private OgrenciRepository ogrenciRepository;

    @Autowired
    private NotTuruRepository notTuruRepository;

    @Autowired
    private IFileStorageService fileStorageService;


    @Override
    public List<DtoDersNotu> findAll() {
        List<DtoDersNotu> dtoDersNotuList = new ArrayList<>();
        List<DersNotu> dersNotuList = dersNotuRepository.findAll();

        if (dersNotuList.isEmpty()) {
            return null;
        }

        for (DersNotu dersNotu : dersNotuList) {
            DtoDersNotu dtoDersNotu = new DtoDersNotu();
            BeanUtils.copyProperties(dersNotu, dtoDersNotu);
            
            if (dersNotu.getOgrenci() != null) {
                DtoOgrenci dtoOgrenci = new DtoOgrenci();
                BeanUtils.copyProperties(dersNotu.getOgrenci(), dtoOgrenci);
                dtoDersNotu.setOgrenci(dtoOgrenci);
            }

            if (dersNotu.getNotTuru() != null) {
                DtoNotTuru dtoNotTuru = new DtoNotTuru();
                BeanUtils.copyProperties(dersNotu.getNotTuru(), dtoNotTuru);
                dtoDersNotu.setNotTuru(dtoNotTuru);
            }
            
            dtoDersNotuList.add(dtoDersNotu);
        }

        return dtoDersNotuList;
    }

    @Override
    public DtoDersNotu findById(Integer dersNotuId) {
        Optional<DersNotu> optionalDersNotu = dersNotuRepository.findById(dersNotuId);
        
        if (optionalDersNotu.isEmpty()) {
            return null;
        }

        DersNotu dersNotu = optionalDersNotu.get();
        DtoDersNotu dtoDersNotu = new DtoDersNotu();
        BeanUtils.copyProperties(dersNotu, dtoDersNotu);

        if (dersNotu.getOgrenci() != null) {
            DtoOgrenci dtoOgrenci = new DtoOgrenci();
            BeanUtils.copyProperties(dersNotu.getOgrenci(), dtoOgrenci);
            dtoDersNotu.setOgrenci(dtoOgrenci);
        }

        if (dersNotu.getNotTuru() != null) {
            DtoNotTuru dtoNotTuru = new DtoNotTuru();
            BeanUtils.copyProperties(dersNotu.getNotTuru(), dtoNotTuru);
            dtoDersNotu.setNotTuru(dtoNotTuru);
        }

        return dtoDersNotu;
    }

    @Override
    public List<DtoDersNotu> getByOgrenciUsername(String username) {

        Optional<Ogrenci> ogrenci = ogrenciRepository.findByUsername(username);

        if (ogrenci.isEmpty()) {
            return Collections.emptyList();
        }

        Ogrenci dbogrenci = ogrenci.get();

        List<DersNotu> dersNotlari = dersNotuRepository.findByOgrenci(dbogrenci);

        List<DtoDersNotu> dersNotuList = new ArrayList<>();

        for (DersNotu dersNotu : dersNotlari) {
            DtoDersNotu dtoDersNotu = new DtoDersNotu();
            BeanUtils.copyProperties(dersNotu, dtoDersNotu);

            DtoOgrenci dtoOgrenci = new DtoOgrenci();
            BeanUtils.copyProperties(dersNotu.getOgrenci(), dtoOgrenci);
            dtoDersNotu.setOgrenci(dtoOgrenci);

            DtoNotTuru dtoNotTuru = new DtoNotTuru();
            BeanUtils.copyProperties(dersNotu.getNotTuru(), dtoNotTuru);
            dtoDersNotu.setNotTuru(dtoNotTuru);

            dersNotuList.add(dtoDersNotu);
        }
        return dersNotuList;
    }

    @Override
    public void deleteById(Integer dersNotuId) {
        dersNotuRepository.deleteById(dersNotuId);
    }

    @Override
    public DtoDersNotu getById(Integer dersNotuId) {

        DtoDersNotu dtoDersNotu = new DtoDersNotu();
        DersNotu dersNotu = dersNotuRepository.findById(dersNotuId).get();

        BeanUtils.copyProperties(dersNotu, dtoDersNotu);

        return dtoDersNotu;
    }

    @Override
    public DtoDersNotu update(DtoDersNotu dtoDersNotu) {
        Optional<DersNotu> optionalDersNotu = dersNotuRepository.findById(dtoDersNotu.getDersNotId());
        
        if (optionalDersNotu.isEmpty()) {
            return null;
        }

        DersNotu existingDersNotu = optionalDersNotu.get();
        BeanUtils.copyProperties(dtoDersNotu, existingDersNotu, "dersNotId");

        if (dtoDersNotu.getOgrenci() != null) {
            Ogrenci ogrenci = new Ogrenci();
            BeanUtils.copyProperties(dtoDersNotu.getOgrenci(), ogrenci);
            existingDersNotu.setOgrenci(ogrenci);
        }

        DersNotu updatedDersNotu = dersNotuRepository.save(existingDersNotu);
        DtoDersNotu updatedDtoDersNotu = new DtoDersNotu();
        BeanUtils.copyProperties(updatedDersNotu, updatedDtoDersNotu);
        return updatedDtoDersNotu;
    }

    @Override
    public DtoDersNotu saveDersNotu(String username, String dersNotAdi, String dersNotIcerik, Integer dersNotFiyat, Integer notTuruId, MultipartFile pdfFile, MultipartFile pdfOnizlemeFile) throws IOException {

        Optional<Ogrenci> optionalOgrenci = ogrenciRepository.findByUsername(username);

        if (optionalOgrenci.isEmpty()) {
            throw new RuntimeException("Kullanıcı bulunamadı: " + username);
        }

        Optional<NotTuru> optionalNotTuru = notTuruRepository.findById(notTuruId);
        if (optionalNotTuru.isEmpty()) {
            throw new RuntimeException("Not türü bulunamadı: " + notTuruId);
        }

        String pdfPath = fileStorageService.storeFile(pdfFile, "ders-notlari");
        String onizlemePath = fileStorageService.storeFile(pdfOnizlemeFile, "ders-notlari-onizleme");

        DersNotu dersNotu = new DersNotu();
        dersNotu.setDersNotAdi(dersNotAdi);
        dersNotu.setDersNotIcerik(dersNotIcerik);
        dersNotu.setDersNotFiyat(dersNotFiyat);
        dersNotu.setDersNotPdf(pdfPath);
        dersNotu.setDersNotPdfOnizleme(onizlemePath);
        dersNotu.setOgrenci(optionalOgrenci.get());
        dersNotu.setNotTuru(optionalNotTuru.get());

        DersNotu savedDersNotu = dersNotuRepository.save(dersNotu);

        DtoDersNotu dtoDersNotu = new DtoDersNotu();
        BeanUtils.copyProperties(savedDersNotu, dtoDersNotu);

        DtoOgrenci dtoOgrenci = new DtoOgrenci();
        BeanUtils.copyProperties(savedDersNotu.getOgrenci(), dtoOgrenci);
        dtoDersNotu.setOgrenci(dtoOgrenci);

        DtoNotTuru dtoNotTuru = new DtoNotTuru();
        BeanUtils.copyProperties(savedDersNotu.getNotTuru(), dtoNotTuru);
        dtoDersNotu.setNotTuru(dtoNotTuru);

        return dtoDersNotu;

    }
} 