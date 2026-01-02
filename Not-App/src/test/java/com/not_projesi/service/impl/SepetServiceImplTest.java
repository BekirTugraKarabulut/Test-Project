package com.not_projesi.service.impl;

import com.not_projesi.dto.SatinAlinanlarDTO;
import com.not_projesi.dto.SepetDTO;
import com.not_projesi.entity.DersNotu;
import com.not_projesi.entity.Ogrenci;
import com.not_projesi.entity.Sepet;
import com.not_projesi.repository.DersNotuRepository;
import com.not_projesi.repository.SepetRepository;
import com.not_projesi.service.ISatinAlinanlarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SepetServiceImplTest {

    @InjectMocks
    private SepetServiceImpl sepetService;

    @Mock
    private SepetRepository sepetRepository;

    @Mock
    private DersNotuRepository dersNotuRepository;

    @Mock
    private ISatinAlinanlarService satinAlinanlarService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sepeteEkle_basarili() {
        SepetDTO dto = new SepetDTO();
        dto.setOgrenciUsername("user1");
        dto.setUrunAdi("Not1");

        Sepet saved = new Sepet();
        Ogrenci ogr = new Ogrenci();
        ogr.setUsername("user1");
        saved.setOgrenci(ogr);

        when(sepetRepository.save(any())).thenReturn(saved);

        SepetDTO result = sepetService.sepeteEkle(dto);

        assertEquals("user1", result.getOgrenciUsername());
        verify(sepetRepository).save(any());
    }

    @Test
    void kullaniciSepeti_listDoner() {
        Ogrenci ogr = new Ogrenci();
        ogr.setUsername("user1");

        Sepet sepet = new Sepet();
        sepet.setOgrenci(ogr);

        when(sepetRepository.findByOgrenciUsername("user1"))
                .thenReturn(List.of(sepet));

        List<SepetDTO> result =
                sepetService.kullaniciSepeti("user1");

        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getOgrenciUsername());
    }

    @Test
    void sepettenUrunSil_cagrilir() {
        sepetService.sepettenUrunSil(5L);

        verify(sepetRepository).deleteById(5L);
    }

    @Test
    void sepetiTemizle_cagrilir() {
        sepetService.sepetiTemizle("user1");

        verify(sepetRepository).deleteByOgrenciUsername("user1");
    }

    @Test
    void sepettekiUrunleriSatınAl_hepsiSatinalinirVeSepetTemizlenir() {

        Sepet sepet = new Sepet();
        sepet.setUrunAdi("Algoritmalar");
        sepet.setFiyat(50.0);

        when(sepetRepository.findByOgrenciUsername("user1"))
                .thenReturn(List.of(sepet));

        DersNotu dersNotu = new DersNotu();
        dersNotu.setDersNotPdf("algo.pdf");

        when(dersNotuRepository.findByDersNotAdi("Algoritmalar"))
                .thenReturn(dersNotu);

        when(satinAlinanlarService.satinAlmaEkle(any(SatinAlinanlarDTO.class)))
                .thenReturn(new SatinAlinanlarDTO());

        sepetService.sepettekiUrunleriSatınAl("user1");

        verify(satinAlinanlarService, times(1))
                .satinAlmaEkle(any(SatinAlinanlarDTO.class));

        verify(sepetRepository).deleteByOgrenciUsername("user1");
    }
}
