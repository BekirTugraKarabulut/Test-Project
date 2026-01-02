package com.not_projesi.controller;

import com.not_projesi.dto.KrediKartDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IKrediKartController {

    KrediKartDTO kartEkle(@RequestBody KrediKartDTO krediKartDTO);

    List<KrediKartDTO> kullaniciKartlari(@PathVariable String username);

    void kartSil(@PathVariable Long id);
    
    List<KrediKartDTO> findByUsername(@PathVariable String username);
    
    List<KrediKartDTO> findAll();
} 