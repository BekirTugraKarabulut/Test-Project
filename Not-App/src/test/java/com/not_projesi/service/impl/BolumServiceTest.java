package com.not_projesi.service.impl;

import com.not_projesi.dto.DtoBolum;
import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.entity.Bolum;
import com.not_projesi.entity.Ogrenci;
import com.not_projesi.repository.BolumRepository;
import com.not_projesi.service.impl.BolumService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BolumServiceTest {

    @Mock
    BolumRepository bolumRepository;

    @InjectMocks
    BolumService bolumService;


    @Test
    void findAll_bosListeGelirseNullDonderir() {
        when(bolumRepository.findAll()).thenReturn(List.of());

        List<DtoBolum> result = bolumService.findAll();

        assertNull(result);
    }

    @Test
    void findAll_kayitlariDtoyaMapler() {

        Ogrenci ogr = new Ogrenci();
        ogr.setUsername("ali");

        Bolum b = new Bolum();
        b.setBolumId(1);
        b.setBolumAdi("Bilgisayar");
        b.setOgrenci(List.of(ogr));

        when(bolumRepository.findAll()).thenReturn(List.of(b));

        List<DtoBolum> result = bolumService.findAll();

        assertEquals(1, result.size());
        assertEquals("Bilgisayar", result.get(0).getBolumAdi());
        assertEquals("ali", result.get(0).getOgrenci().get(0).getUsername());
    }


    @Test
    void findById_yoksaNullDoner() {

        when(bolumRepository.findById(5)).thenReturn(Optional.empty());

        DtoBolum result = bolumService.findById(5);

        assertNull(result);
    }

    @Test
    void findById_varsaDtoDoner() {

        Bolum bolum = new Bolum();
        bolum.setBolumId(2);
        bolum.setBolumAdi("Makine");

        when(bolumRepository.findById(2)).thenReturn(Optional.of(bolum));

        DtoBolum result = bolumService.findById(2);

        assertNotNull(result);
        assertEquals("Makine", result.getBolumAdi());
    }


    @Test
    void save_dtoyuKaydedipGeriDonderir() {

        DtoBolum dto = new DtoBolum();
        dto.setBolumAdi("Elektrik");

        Bolum saved = new Bolum();
        saved.setBolumId(10);
        saved.setBolumAdi("Elektrik");

        when(bolumRepository.save(any(Bolum.class))).thenReturn(saved);

        DtoBolum result = bolumService.save(dto);

        assertEquals(10, result.getBolumId());
        verify(bolumRepository).save(any(Bolum.class));
    }


    @Test
    void deleteById_repositoryCalisir() {

        bolumService.deleteById(3);

        verify(bolumRepository).deleteById(3);
    }


    @Test
    void update_kayitYoksaNullDoner() {

        DtoBolum dto = new DtoBolum();
        dto.setBolumId(99);

        when(bolumRepository.findById(99)).thenReturn(Optional.empty());

        assertNull(bolumService.update(dto));
    }

    @Test
    void update_kaydiGuncellerVeDtoDoner() {

        Bolum existing = new Bolum();
        existing.setBolumId(1);
        existing.setBolumAdi("Eski");

        when(bolumRepository.findById(1)).thenReturn(Optional.of(existing));

        Bolum updated = new Bolum();
        updated.setBolumId(1);
        updated.setBolumAdi("Yeni");

        when(bolumRepository.save(any(Bolum.class))).thenReturn(updated);

        DtoBolum dto = new DtoBolum();
        dto.setBolumId(1);
        dto.setBolumAdi("Yeni");

        DtoBolum result = bolumService.update(dto);

        assertEquals("Yeni", result.getBolumAdi());
    }
}
