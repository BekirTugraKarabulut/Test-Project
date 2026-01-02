package com.not_projesi.service.impl;

import com.not_projesi.dto.SatinAlinanlarDTO;
import com.not_projesi.entity.DersNotu;
import com.not_projesi.entity.Ogrenci;
import com.not_projesi.entity.SatinAlinanlar;
import com.not_projesi.repository.SatinAlinanlarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SatinAlinanlarServiceImplTest {

    @Mock
    private SatinAlinanlarRepository satinAlinanlarRepository;

    @InjectMocks
    private SatinAlinanlarServiceImpl satinAlinanlarService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void satinAlmaEkle_basarili() {
        SatinAlinanlarDTO dto = new SatinAlinanlarDTO();
        dto.setOgrenciUsername("user1");

        SatinAlinanlar entity = new SatinAlinanlar();
        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setUsername("user1");
        entity.setOgrenci(ogrenci);

        when(satinAlinanlarRepository.save(any())).thenReturn(entity);

        SatinAlinanlarDTO result = satinAlinanlarService.satinAlmaEkle(dto);

        assertEquals("user1", result.getOgrenciUsername());
        verify(satinAlinanlarRepository).save(any());
    }

    @Test
    void kullaniciSatinAlmalari_listDoner() {
        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setUsername("user1");

        SatinAlinanlar satin = new SatinAlinanlar();
        satin.setOgrenci(ogrenci);

        when(satinAlinanlarRepository.findByOgrenciUsername("user1"))
                .thenReturn(List.of(satin));

        List<SatinAlinanlarDTO> result =
                satinAlinanlarService.kullaniciSatinAlmalari("user1");

        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getOgrenciUsername());
    }

    @Test
    void odemeYap_basarili() {
        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setUsername("user1");

        SatinAlinanlar satin = new SatinAlinanlar();
        satin.setId(1L);
        satin.setOgrenci(ogrenci);
        satin.setOdemeDurumu(false);

        when(satinAlinanlarRepository.findById(1L))
                .thenReturn(Optional.of(satin));

        when(satinAlinanlarRepository.save(any()))
                .thenReturn(satin);

        SatinAlinanlarDTO result = satinAlinanlarService.odemeYap(1L);

        assertTrue(result.getOdemeDurumu());
        verify(satinAlinanlarRepository).save(any());
    }

    @Test
    void findByUsername_dersNotuYoksa_nullAlanlarKorunur() {
        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setUsername("user1");

        SatinAlinanlar satin = new SatinAlinanlar();
        satin.setOgrenci(ogrenci);

        when(satinAlinanlarRepository.findByOgrenciUsername("user1"))
                .thenReturn(List.of(satin));

        List<SatinAlinanlarDTO> result =
                satinAlinanlarService.findByUsername("user1");

        assertNull(result.get(0).getDersNotAdi());
        assertNull(result.get(0).getPdfUrl());
    }

    @Test
    void findByUsername_dersNotuVarken_bilgilerHaritalanir() {
        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setUsername("user1");

        DersNotu dersNotu = new DersNotu();
        dersNotu.setDersNotAdi("Algoritmalar");
        dersNotu.setDersNotPdf("algo.pdf");

        SatinAlinanlar satin = new SatinAlinanlar();
        satin.setOgrenci(ogrenci);
        satin.setDersNotu(dersNotu);

        when(satinAlinanlarRepository.findByOgrenciUsername("user1"))
                .thenReturn(List.of(satin));

        List<SatinAlinanlarDTO> result =
                satinAlinanlarService.findByUsername("user1");

        assertEquals("Algoritmalar", result.get(0).getDersNotAdi());
        assertEquals("algo.pdf", result.get(0).getPdfUrl());
    }

    @Test
    void findAll_hepsiniDonderir() {
        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setUsername("user1");

        SatinAlinanlar satin = new SatinAlinanlar();
        satin.setOgrenci(ogrenci);

        when(satinAlinanlarRepository.findAll())
                .thenReturn(List.of(satin));

        List<SatinAlinanlarDTO> result =
                satinAlinanlarService.findAll();

        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getOgrenciUsername());
    }

    @Test
    void satinAlinanUrunuSil_basarili() {
        SatinAlinanlar satin = new SatinAlinanlar();
        satin.setId(1L);

        when(satinAlinanlarRepository.findById(1L))
                .thenReturn(Optional.of(satin));

        satinAlinanlarService.satinAlinanUrunuSil(1L);

        verify(satinAlinanlarRepository).delete(satin);
    }
}
