package com.not_projesi.service;

import com.not_projesi.dto.KrediKartDTO;
import java.util.List;

public interface IKrediKartService {

    KrediKartDTO kartEkle(KrediKartDTO krediKartDTO);

    List<KrediKartDTO> kullaniciKartlari(String username);

    void kartSil(Long id);

    List<KrediKartDTO> findByUsername(String username);

    List<KrediKartDTO> findAll();
    
} 