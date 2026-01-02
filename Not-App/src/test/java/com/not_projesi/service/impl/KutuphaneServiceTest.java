package com.not_projesi.service.impl;

import com.not_projesi.dto.DtoKutuphane;
import com.not_projesi.entity.Kutuphane;
import com.not_projesi.repository.KutuphaneRepository;
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
class KutuphaneServiceTest {

    @Mock
    KutuphaneRepository kutuphaneRepository;

    @InjectMocks
    KutuphaneService kutuphaneService;

    @Test
    void findAll_listeVarkenDTOyaMapler() {
        Kutuphane k1 = new Kutuphane();
        k1.setKutupId(1);
        k1.setKutupAdi("Merkez");

        when(kutuphaneRepository.findAll()).thenReturn(List.of(k1));

        List<DtoKutuphane> result = kutuphaneService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Merkez", result.get(0).getKutupAdi());
    }

    @Test
    void findAll_bosListeDonerseNullDondurur() {
        when(kutuphaneRepository.findAll()).thenReturn(List.of());

        List<DtoKutuphane> result = kutuphaneService.findAll();

        assertNull(result);
    }

    @Test
    void findById_kayitVarkenDTOyaMapler() {
        Kutuphane k = new Kutuphane();
        k.setKutupId(5);
        k.setKutupAdi("Bilgi");

        when(kutuphaneRepository.findById(5)).thenReturn(Optional.of(k));

        DtoKutuphane result = kutuphaneService.findById(5);

        assertNotNull(result);
        assertEquals("Bilgi", result.getKutupAdi());
    }

    @Test
    void findById_yoksaNullDoner() {
        when(kutuphaneRepository.findById(10)).thenReturn(Optional.empty());

        DtoKutuphane result = kutuphaneService.findById(10);

        assertNull(result);
    }

    @Test
    void save_kaydedipDTOOlarakDondurur() {
        DtoKutuphane dto = new DtoKutuphane();
        dto.setKutupAdi("Yeni");

        Kutuphane saved = new Kutuphane();
        saved.setKutupId(3);
        saved.setKutupAdi("Yeni");

        when(kutuphaneRepository.save(any(Kutuphane.class))).thenReturn(saved);

        DtoKutuphane result = kutuphaneService.save(dto);

        assertEquals(3, result.getKutupId());
        assertEquals("Yeni", result.getKutupAdi());
        verify(kutuphaneRepository).save(any(Kutuphane.class));
    }

    @Test
    void deleteById_deleteCagirir() {
        kutuphaneService.deleteById(7);
        verify(kutuphaneRepository).deleteById(7);
    }

    @Test
    void update_kayitVarkenGuncellerVeDTOOlarakDonderir() {
        DtoKutuphane dto = new DtoKutuphane();
        dto.setKutupId(4);
        dto.setKutupAdi("Guncel");

        Kutuphane existing = new Kutuphane();
        existing.setKutupId(4);
        existing.setKutupAdi("Eski");

        Kutuphane updated = new Kutuphane();
        updated.setKutupId(4);
        updated.setKutupAdi("Guncel");

        when(kutuphaneRepository.findById(4)).thenReturn(Optional.of(existing));
        when(kutuphaneRepository.save(existing)).thenReturn(updated);

        DtoKutuphane result = kutuphaneService.update(dto);

        assertNotNull(result);
        assertEquals("Guncel", result.getKutupAdi());
    }

    @Test
    void update_kayitYoksaNullDoner() {
        DtoKutuphane dto = new DtoKutuphane();
        dto.setKutupId(99);

        when(kutuphaneRepository.findById(99)).thenReturn(Optional.empty());

        DtoKutuphane result = kutuphaneService.update(dto);

        assertNull(result);
    }
}
