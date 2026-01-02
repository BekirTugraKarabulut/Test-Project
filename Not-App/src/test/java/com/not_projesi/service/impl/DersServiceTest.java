package com.not_projesi.service.impl;

import com.not_projesi.dto.DtoDers;
import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.dto.DtoOgretimUyesi;
import com.not_projesi.entity.Ders;
import com.not_projesi.entity.Ogrenci;
import com.not_projesi.entity.OgretimUyesi;
import com.not_projesi.repository.DersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DersServiceTest {

    @Mock
    private DersRepository dersRepository;

    @InjectMocks
    private DersService dersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_whenExists_returnsDtos() {
        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setUsername("ali");

        OgretimUyesi ou = new OgretimUyesi();
        ou.setOgretimUyesiAdi("Ahmet");

        Ders ders = new Ders();
        ders.setDersId(1);
        ders.setDersAdi("Matematik");
        ders.setOgrenci(ogrenci);
        ders.setOgretimUyesiList(List.of(ou));

        when(dersRepository.findAll()).thenReturn(List.of(ders));

        var result = dersService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Matematik", result.get(0).getDersAdi());
        assertEquals("ali", result.get(0).getOgrenci().getUsername());
        assertEquals(1, result.get(0).getOgretimUyesiList().size());
    }

    @Test
    void findAll_whenEmpty_returnsNull() {
        when(dersRepository.findAll()).thenReturn(List.of());
        assertNull(dersService.findAll());
    }

    @Test
    void findById_whenExists_returnsDto() {
        Ders ders = new Ders();
        ders.setDersId(10);
        ders.setDersAdi("Fizik");

        when(dersRepository.findById(10)).thenReturn(Optional.of(ders));

        var dto = dersService.findById(10);

        assertNotNull(dto);
        assertEquals(10, dto.getDersId());
        assertEquals("Fizik", dto.getDersAdi());
    }

    @Test
    void findById_whenNotExists_returnsNull() {
        when(dersRepository.findById(5)).thenReturn(Optional.empty());
        assertNull(dersService.findById(5));
    }

    @Test
    void save_persistsAndReturnsDto() {
        DtoDers dto = new DtoDers();
        dto.setDersAdi("Kimya");

        DtoOgrenci dtoOgrenci = new DtoOgrenci();
        dtoOgrenci.setUsername("veli");
        dto.setOgrenci(dtoOgrenci);

        DtoOgretimUyesi dtoOu = new DtoOgretimUyesi();
        dtoOu.setOgretimUyesiAdi("Mehmet");
        dto.setOgretimUyesiList(List.of(dtoOu));

        when(dersRepository.save(any())).thenAnswer(a -> {
            Ders d = a.getArgument(0);
            d.setDersId(20);
            return d;
        });

        var result = dersService.save(dto);

        assertNotNull(result);
        assertEquals(20, result.getDersId());
        assertEquals("Kimya", result.getDersAdi());
    }

    @Test
    void deleteById_callsRepository() {
        dersService.deleteById(99);
        verify(dersRepository, times(1)).deleteById(99);
    }

    @Test
    void update_whenNotExists_returnsNull() {
        DtoDers dto = new DtoDers();
        dto.setDersId(7);

        when(dersRepository.findById(7)).thenReturn(Optional.empty());

        assertNull(dersService.update(dto));
    }

    @Test
    void update_whenExists_updatesAndReturnsDto() {
        Ders existing = new Ders();
        existing.setDersId(7);
        existing.setDersAdi("Eski Ad");

        when(dersRepository.findById(7)).thenReturn(Optional.of(existing));
        when(dersRepository.save(any())).thenAnswer(a -> a.getArgument(0));

        DtoDers dto = new DtoDers();
        dto.setDersId(7);
        dto.setDersAdi("Yeni Ad");

        var result = dersService.update(dto);

        assertNotNull(result);
        assertEquals("Yeni Ad", result.getDersAdi());
    }

}
