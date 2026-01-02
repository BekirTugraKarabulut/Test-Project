package com.not_projesi.service.impl;

import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.entity.Bolum;
import com.not_projesi.entity.Ogrenci;
import com.not_projesi.repository.OgrenciRepository;
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
class OgrenciServiceTest {

    @Mock
    OgrenciRepository ogrenciRepository;

    @InjectMocks
    OgrenciService ogrenciService;

    @Test
    void findAll_listeBosIseNullDoner() {
        when(ogrenciRepository.findAll()).thenReturn(List.of());

        List<DtoOgrenci> result = ogrenciService.findAll();

        assertNull(result);
    }

    @Test
    void findAll_listeDoluykenDTOyaDonusturur() {
        Ogrenci o = new Ogrenci();
        o.setUsername("ali");
        o.setOgrenciAdi("Ali");

        when(ogrenciRepository.findAll()).thenReturn(List.of(o));

        List<DtoOgrenci> result = ogrenciService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ali", result.get(0).getUsername());
        assertEquals("Ali", result.get(0).getOgrenciAdi());
    }

    @Test
    void findDtoOgrenciByUsername_kayitYoksaNullDoner() {
        when(ogrenciRepository.findByUsername("xxx")).thenReturn(Optional.empty());

        DtoOgrenci result = ogrenciService.findDtoOgrenciByUsername("xxx");

        assertNull(result);
    }

    @Test
    void findDtoOgrenciByUsername_kayitVarsaDTOyaMaplerVeBolumAtar() {
        Bolum bolum = new Bolum();
        bolum.setBolumId(5);
        bolum.setBolumAdi("Bilgisayar");

        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setUsername("veli");
        ogrenci.setOgrenciAdi("Veli");
        ogrenci.setBolumList(bolum);

        when(ogrenciRepository.findByUsername("veli")).thenReturn(Optional.of(ogrenci));

        DtoOgrenci result = ogrenciService.findDtoOgrenciByUsername("veli");

        assertNotNull(result);
        assertEquals("veli", result.getUsername());
        assertEquals("Veli", result.getOgrenciAdi());
        assertNotNull(result.getDtoBolum());
        assertEquals("Bilgisayar", result.getDtoBolum().getBolumAdi());
    }
}
