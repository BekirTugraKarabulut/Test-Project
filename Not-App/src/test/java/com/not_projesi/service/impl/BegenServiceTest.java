package com.not_projesi.service.impl;

import com.not_projesi.dto.*;
import com.not_projesi.entity.*;
import com.not_projesi.exception.BaseException;
import com.not_projesi.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BegenServiceTest {

    @Mock
    BegenRepository begenRepository;

    @Mock
    OgrenciRepository ogrenciRepository;

    @Mock
    DersNotuRepository dersNotuRepository;

    @InjectMocks
    BegenService begenService;

    @Test
    void findAll_begenVarIseDtoListDonderir() {
        Ogrenci ogr = new Ogrenci();
        ogr.setUsername("ali");

        Begen b = new Begen();
        b.setBegenId(1);
        b.setOgrenci(ogr);

        when(begenRepository.findAll()).thenReturn(List.of(b));

        List<DtoBegen> result = begenService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ali", result.get(0).getOgrenci().getUsername());
    }

    @Test
    void findAll_bosListeIseNullDonderir() {
        when(begenRepository.findAll()).thenReturn(List.of());

        List<DtoBegen> result = begenService.findAll();

        assertNull(result);
    }


    @Test
    void save_basariliSekildeKaydeder() {

        DtoBegenUI dto = new DtoBegenUI();

        DtoOgrenci dtoOgrenci = new DtoOgrenci();
        dtoOgrenci.setUsername("ali");
        dto.setOgrenci(dtoOgrenci);

        DtoDersNotu dtoNot = new DtoDersNotu();
        dtoNot.setDersNotId(10);
        dto.setDersNotu(dtoNot);

        Ogrenci ogr = new Ogrenci();
        ogr.setUsername("ali");

        DersNotu dersNotu = new DersNotu();
        dersNotu.setDersNotId(10);

        when(ogrenciRepository.findByUsername("ali")).thenReturn(Optional.of(ogr));
        when(dersNotuRepository.findById(10)).thenReturn(Optional.of(dersNotu));

        Begen saved = new Begen();
        saved.setBegenId(5);
        saved.setOgrenci(ogr);
        saved.setDersNotu(dersNotu);

        when(begenRepository.save(any(Begen.class))).thenReturn(saved);

        DtoBegen result = begenService.save(dto);

        assertEquals(5, result.getBegenId());
        assertEquals("ali", result.getOgrenci().getUsername());
        verify(begenRepository).save(any(Begen.class));
    }

    @Test
    void save_ogrenciYoksaExceptionAtar() {

        DtoBegenUI dto = new DtoBegenUI();
        DtoOgrenci o = new DtoOgrenci();
        o.setUsername("ali");
        dto.setOgrenci(o);

        when(ogrenciRepository.findByUsername("ali"))
                .thenReturn(Optional.empty());

        assertThrows(BaseException.class, () -> begenService.save(dto));
    }

    @Test
    void save_dersNotuYoksaExceptionAtar() {

        DtoBegenUI dto = new DtoBegenUI();
        DtoOgrenci o = new DtoOgrenci();
        o.setUsername("ali");
        dto.setOgrenci(o);

        DtoDersNotu dn = new DtoDersNotu();
        dn.setDersNotId(20);
        dto.setDersNotu(dn);

        when(ogrenciRepository.findByUsername("ali"))
                .thenReturn(Optional.of(new Ogrenci()));

        when(dersNotuRepository.findById(20))
                .thenReturn(Optional.empty());

        assertThrows(BaseException.class, () -> begenService.save(dto));
    }


    @Test
    void deleteById_sadeceRepositoryyiCagirir() {
        begenService.deleteById(7);
        verify(begenRepository).deleteById(7);
    }


    @Test
    void getBegenByUsername_sadeceOgrenciyeAitKayitlariDonderir() {

        Ogrenci ali = new Ogrenci();
        ali.setUsername("ali");

        Ogrenci veli = new Ogrenci();
        veli.setUsername("veli");

        DersNotu dn = new DersNotu();
        dn.setDersNotId(1);

        Begen b1 = new Begen();
        b1.setOgrenci(ali);
        b1.setDersNotu(dn);

        Begen b2 = new Begen();
        b2.setOgrenci(veli);
        b2.setDersNotu(dn);

        when(begenRepository.findAll()).thenReturn(List.of(b1, b2));

        List<DtoBegen> result = begenService.getBegenByUsername("ali");

        assertEquals(1, result.size());
        assertEquals("ali", result.get(0).getOgrenci().getUsername());
    }


    @Test
    void update_kayitVarIseGunceller() {

        Begen existing = new Begen();
        existing.setBegenId(3);

        when(begenRepository.findById(3)).thenReturn(Optional.of(existing));
        when(begenRepository.save(any(Begen.class))).thenReturn(existing);

        DtoBegen dto = new DtoBegen();
        dto.setBegenId(3);

        DtoBegen result = begenService.update(dto);

        assertNotNull(result);
        verify(begenRepository).save(any(Begen.class));
    }

    @Test
    void update_kayitYoksaNullDonderir() {

        when(begenRepository.findById(99)).thenReturn(Optional.empty());

        DtoBegen dto = new DtoBegen();
        dto.setBegenId(99);

        DtoBegen result = begenService.update(dto);

        assertNull(result);
    }
}
