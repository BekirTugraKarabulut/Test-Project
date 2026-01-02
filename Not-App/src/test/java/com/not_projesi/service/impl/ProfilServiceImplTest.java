package com.not_projesi.service.impl;

import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.dto.DtoOgrenciUI;
import com.not_projesi.entity.Bolum;
import com.not_projesi.entity.Fakulte;
import com.not_projesi.entity.Ogrenci;
import com.not_projesi.repository.ProfilRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfilServiceImplTest {

    @Mock
    ProfilRepository profilRepository;

    @InjectMocks
    ProfilServiceImpl profilService;

    private Ogrenci prepareSampleOgrenci() {
        Fakulte fakulte = new Fakulte();
        fakulte.setFakulteAdi("Mühendislik");

        Bolum bolum = new Bolum();
        bolum.setBolumAdi("Bilgisayar");
        bolum.setFakulte(fakulte);

        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setUsername("ali");
        ogrenci.setOgrenciAdi("Ali");
        ogrenci.setOgrenciSoyadi("Yılmaz");
        ogrenci.setOgrenciEmail("ali@mail.com");
        ogrenci.setOgrenciSifre("123");
        ogrenci.setBolumList(bolum);

        return ogrenci;
    }


    @Test
    void ogrenciBilgileriByUsername_varsaDTOyaMaplenir() {
        Ogrenci ogrenci = prepareSampleOgrenci();
        when(profilRepository.findByUsername("ali")).thenReturn(ogrenci);

        DtoOgrenci result = profilService.ogrenciBilgileriByUsername("ali");

        assertNotNull(result);
        assertEquals("ali", result.getUsername());
        assertEquals("Bilgisayar", result.getDtoBolum().getBolumAdi());
        assertEquals("Mühendislik", result.getDtoBolum().getFakulte().getFakulteAdi());
    }


    @Test
    void guncelleOgrenciByUsername_kayitYoksaNullDoner() {

        when(profilRepository.findByUsername("x"))
                .thenReturn(null);   // <-- doğru

        DtoOgrenci result =
                profilService.guncelleOgrenciByUsername("x", new DtoOgrenciUI());

        assertNull(result);
    }


    @Test
    void guncelleOgrenciByUsername_degerlerGuncellenirVeDTOdoner() {
        Ogrenci ogrenci = prepareSampleOgrenci();
        when(profilRepository.findByUsername("ali")).thenReturn(ogrenci);
        when(profilRepository.save(any(Ogrenci.class))).thenAnswer(inv -> inv.getArgument(0));

        DtoOgrenciUI update = new DtoOgrenciUI();
        update.setOgrenciAdi("YeniAli");
        update.setOgrenciSoyadi("YeniSoyad");

        var dtoBolum = new com.not_projesi.dto.DtoBolum();
        dtoBolum.setBolumAdi("YeniBolum");

        var fakulteDto = new com.not_projesi.dto.DtoFakulte();
        fakulteDto.setFakulteAdi("YeniFakulte");

        dtoBolum.setFakulte(fakulteDto);
        update.setDtoBolum(dtoBolum);

        DtoOgrenci result = profilService.guncelleOgrenciByUsername("ali", update);

        assertNotNull(result);
        assertEquals("YeniAli", result.getOgrenciAdi());
        assertEquals("YeniBolum", result.getDtoBolum().getBolumAdi());
        assertEquals("YeniFakulte", result.getDtoBolum().getFakulte().getFakulteAdi());

        verify(profilRepository).save(any(Ogrenci.class));
    }
}
