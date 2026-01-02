package com.not_projesi.service.impl;

import com.not_projesi.dto.KrediKartDTO;
import com.not_projesi.entity.KrediKart;
import com.not_projesi.entity.Ogrenci;
import com.not_projesi.repository.KrediKartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KrediKartServiceImplTest {

    @Mock
    KrediKartRepository krediKartRepository;

    @InjectMocks
    KrediKartServiceImpl krediKartService;

    @Test
    void kartEkle_kaydedipDTOOlarakDondurur() {
        KrediKartDTO dto = new KrediKartDTO();
        dto.setKartNo("1111");
        dto.setOgrenciUsername("ali");

        KrediKart saved = new KrediKart();
        saved.setKartNo("1111");

        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setUsername("ali");
        saved.setOgrenci(ogrenci);

        when(krediKartRepository.save(any(KrediKart.class))).thenReturn(saved);

        KrediKartDTO result = krediKartService.kartEkle(dto);

        assertEquals("1111", result.getKartNo());
        assertEquals("ali", result.getOgrenciUsername());
        verify(krediKartRepository).save(any(KrediKart.class));
    }

    @Test
    void kullaniciKartlari_sadeceVerilenKullaniciyaAitKartlariDondurur() {
        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setUsername("veli");

        KrediKart k1 = new KrediKart();
        k1.setKartNo("1234");
        k1.setOgrenci(ogrenci);

        when(krediKartRepository.findByOgrenciUsername("veli"))
                .thenReturn(List.of(k1));

        List<KrediKartDTO> result = krediKartService.kullaniciKartlari("veli");

        assertEquals(1, result.size());
        assertEquals("1234", result.get(0).getKartNo());
        assertEquals("veli", result.get(0).getOgrenciUsername());
    }

    @Test
    void kartSil_deleteByIdCagirir() {
        krediKartService.kartSil(5L);
        verify(krediKartRepository).deleteById(5L);
    }

    @Test
    void findByUsername_listeyiDTOyaMapler() {
        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setUsername("ali");

        KrediKart k1 = new KrediKart();
        k1.setKartNo("9999");
        k1.setOgrenci(ogrenci);

        when(krediKartRepository.findByOgrenciUsername("ali"))
                .thenReturn(List.of(k1));

        List<KrediKartDTO> result = krediKartService.findByUsername("ali");

        assertEquals(1, result.size());
        assertEquals("9999", result.get(0).getKartNo());
        assertEquals("ali", result.get(0).getOgrenciUsername());
    }

    @Test
    void findAll_tumKartlariDTOyaMapler() {
        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setUsername("mehmet");

        KrediKart k1 = new KrediKart();
        k1.setKartNo("0000");
        k1.setOgrenci(ogrenci);

        when(krediKartRepository.findAll()).thenReturn(List.of(k1));

        List<KrediKartDTO> result = krediKartService.findAll();

        assertEquals(1, result.size());
        assertEquals("0000", result.get(0).getKartNo());
        assertEquals("mehmet", result.get(0).getOgrenciUsername());
    }
}
