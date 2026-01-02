package com.not_projesi.service.impl;

import com.not_projesi.dto.SatinAlinanlarDTO;
import com.not_projesi.entity.SatinAlinanlar;
import com.not_projesi.entity.Ogrenci;
import com.not_projesi.repository.SatinAlinanlarRepository;
import com.not_projesi.service.ISatinAlinanlarService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SatinAlinanlarServiceImpl implements ISatinAlinanlarService {

    @Autowired
    private SatinAlinanlarRepository satinAlinanlarRepository;

    @Override
    public SatinAlinanlarDTO satinAlmaEkle(SatinAlinanlarDTO satinAlinanlarDTO) {
        SatinAlinanlar satinAlinanlar = new SatinAlinanlar();
        BeanUtils.copyProperties(satinAlinanlarDTO, satinAlinanlar);


        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setUsername(satinAlinanlarDTO.getOgrenciUsername());
        satinAlinanlar.setOgrenci(ogrenci);
        satinAlinanlar.getPdfUrl();

        SatinAlinanlar savedSatinAlinanlar = satinAlinanlarRepository.save(satinAlinanlar);

        SatinAlinanlarDTO responseDTO = new SatinAlinanlarDTO();
        BeanUtils.copyProperties(savedSatinAlinanlar, responseDTO);
        responseDTO.setOgrenciUsername(savedSatinAlinanlar.getOgrenci().getUsername());
        return responseDTO;
    }

    @Override
    public List<SatinAlinanlarDTO> kullaniciSatinAlmalari(String username) {
        List<SatinAlinanlar> satinAlinanlarList = satinAlinanlarRepository.findByOgrenciUsername(username);
        List<SatinAlinanlarDTO> dtoList = new ArrayList<>();

        for (SatinAlinanlar satinAlinanlar : satinAlinanlarList) {
            SatinAlinanlarDTO dto = new SatinAlinanlarDTO();
            BeanUtils.copyProperties(satinAlinanlar, dto);
            dto.setOgrenciUsername(satinAlinanlar.getOgrenci().getUsername());
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public SatinAlinanlarDTO odemeYap(Long id) {
        SatinAlinanlar satinAlinanlar = satinAlinanlarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Satın alma bulunamadı"));

        satinAlinanlar.setOdemeDurumu(true);
        SatinAlinanlar updatedSatinAlinanlar = satinAlinanlarRepository.save(satinAlinanlar);

        SatinAlinanlarDTO responseDTO = new SatinAlinanlarDTO();
        BeanUtils.copyProperties(updatedSatinAlinanlar, responseDTO);
        responseDTO.setOgrenciUsername(updatedSatinAlinanlar.getOgrenci().getUsername());
        return responseDTO;
    }

    @Override
    public List<SatinAlinanlarDTO> findByUsername(String username) {
        List<SatinAlinanlar> satinAlinanlarList = satinAlinanlarRepository.findByOgrenciUsername(username);
        List<SatinAlinanlarDTO> dtoList = new ArrayList<>();



        for (SatinAlinanlar satinAlinanlar : satinAlinanlarList) {
            SatinAlinanlarDTO dto = new SatinAlinanlarDTO();
            BeanUtils.copyProperties(satinAlinanlar, dto);
            dto.setOgrenciUsername(satinAlinanlar.getOgrenci().getUsername());

            if (satinAlinanlar.getDersNotu() != null) {

              dto.setDersNotAdi(satinAlinanlar.getDersNotu().getDersNotAdi());
              dto.setPdfUrl(satinAlinanlar.getDersNotu().getDersNotPdf());
            }
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public List<SatinAlinanlarDTO> findAll() {
        List<SatinAlinanlar> satinAlinanlarList = satinAlinanlarRepository.findAll();
        List<SatinAlinanlarDTO> dtoList = new ArrayList<>();

        for (SatinAlinanlar satinAlinanlar : satinAlinanlarList) {
            SatinAlinanlarDTO dto = new SatinAlinanlarDTO();
            BeanUtils.copyProperties(satinAlinanlar, dto);
            dto.setOgrenciUsername(satinAlinanlar.getOgrenci().getUsername());
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public void satinAlinanUrunuSil(Long id) {
        SatinAlinanlar satinAlinanlar = satinAlinanlarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Satın alınan ürün bulunamadı"));
        satinAlinanlarRepository.delete(satinAlinanlar);
    }
}