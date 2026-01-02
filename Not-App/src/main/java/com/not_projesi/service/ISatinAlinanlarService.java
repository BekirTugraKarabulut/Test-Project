package com.not_projesi.service;

import com.not_projesi.dto.SatinAlinanlarDTO;
import java.util.List;

public interface ISatinAlinanlarService {

    SatinAlinanlarDTO satinAlmaEkle(SatinAlinanlarDTO satinAlinanlarDTO);

    List<SatinAlinanlarDTO> kullaniciSatinAlmalari(String username);

    SatinAlinanlarDTO odemeYap(Long id);

    List<SatinAlinanlarDTO> findByUsername(String username);

    List<SatinAlinanlarDTO> findAll();

    void satinAlinanUrunuSil(Long id);

}