package com.not_projesi.service.impl;

import com.not_projesi.dto.KrediKartDTO;
import com.not_projesi.entity.KrediKart;
import com.not_projesi.entity.Ogrenci;
import com.not_projesi.repository.KrediKartRepository;
import com.not_projesi.service.IKrediKartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KrediKartServiceImpl implements IKrediKartService {

    @Autowired
    private KrediKartRepository krediKartRepository;

    @Override
    public KrediKartDTO kartEkle(KrediKartDTO krediKartDTO) {
        KrediKart krediKart = new KrediKart();
        BeanUtils.copyProperties(krediKartDTO, krediKart);
        
        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setUsername(krediKartDTO.getOgrenciUsername());
        krediKart.setOgrenci(ogrenci);
        
        KrediKart savedKrediKart = krediKartRepository.save(krediKart);
        
        KrediKartDTO responseDTO = new KrediKartDTO();
        BeanUtils.copyProperties(savedKrediKart, responseDTO);
        responseDTO.setOgrenciUsername(savedKrediKart.getOgrenci().getUsername());
        return responseDTO;
    }

    @Override
    public List<KrediKartDTO> kullaniciKartlari(String username) {
        List<KrediKart> krediKartList = krediKartRepository.findByOgrenciUsername(username);
        List<KrediKartDTO> dtoList = new ArrayList<>();
        
        for (KrediKart krediKart : krediKartList) {
            KrediKartDTO dto = new KrediKartDTO();
            BeanUtils.copyProperties(krediKart, dto);
            dto.setOgrenciUsername(krediKart.getOgrenci().getUsername());
            dtoList.add(dto);
        }
        
        return dtoList;
    }

    @Override
    public void kartSil(Long id) {
        krediKartRepository.deleteById(id);
    }

    @Override
    public List<KrediKartDTO> findByUsername(String username) {
        List<KrediKart> krediKartList = krediKartRepository.findByOgrenciUsername(username);
        List<KrediKartDTO> dtoList = new ArrayList<>();
        
        for (KrediKart krediKart : krediKartList) {
            KrediKartDTO dto = new KrediKartDTO();
            BeanUtils.copyProperties(krediKart, dto);
            dto.setOgrenciUsername(krediKart.getOgrenci().getUsername());
            dtoList.add(dto);
        }
        
        return dtoList;
    }

    @Override
    public List<KrediKartDTO> findAll() {
        List<KrediKart> krediKartList = krediKartRepository.findAll();
        List<KrediKartDTO> dtoList = new ArrayList<>();
        
        for (KrediKart krediKart : krediKartList) {
            KrediKartDTO dto = new KrediKartDTO();
            BeanUtils.copyProperties(krediKart, dto);
            dto.setOgrenciUsername(krediKart.getOgrenci().getUsername());
            dtoList.add(dto);
        }
        
        return dtoList;
    }
} 