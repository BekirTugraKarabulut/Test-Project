package com.not_projesi.service;

import com.not_projesi.dto.SepetDTO;
import java.util.List;

public interface ISepetService {
    SepetDTO sepeteEkle(SepetDTO sepetDTO);

    List<SepetDTO> kullaniciSepeti(String username);

    void sepettenUrunSil(Long id);

    void sepetiTemizle(String username);

    void sepettekiUrunleriSatınAl(String username);
}