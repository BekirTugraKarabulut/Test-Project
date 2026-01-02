package com.not_projesi.controller;

import com.not_projesi.dto.SatinAlinanlarDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ISatinAlinanlarController {

    SatinAlinanlarDTO satinAlmaEkle(@RequestBody SatinAlinanlarDTO satinAlinanlarDTO);

    List<SatinAlinanlarDTO> kullaniciSatinAlmalari(@PathVariable String username);

    SatinAlinanlarDTO odemeYap(@PathVariable Long id);
    
    List<SatinAlinanlarDTO> findByUsername(@PathVariable String username);
    
    List<SatinAlinanlarDTO> findAll();
} 