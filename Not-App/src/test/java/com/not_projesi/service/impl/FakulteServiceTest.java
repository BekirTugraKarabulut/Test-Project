package com.not_projesi.service.impl;

import com.not_projesi.dto.DtoBolum;
import com.not_projesi.dto.DtoFakulte;
import com.not_projesi.entity.Bolum;
import com.not_projesi.entity.Fakulte;
import com.not_projesi.repository.FakulteRepository;
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
class FakulteServiceTest {

    @Mock
    private FakulteRepository fakulteRepository;

    @InjectMocks
    private FakulteService fakulteService;

    @Test
    void findAll_whenListExists_returnsDtos() {
        Fakulte f = new Fakulte();
        f.setFakulteId(1);

        when(fakulteRepository.findAll()).thenReturn(List.of(f));

        var result = fakulteService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getFakulteId());
    }

    @Test
    void findAll_whenEmpty_returnsNull() {
        when(fakulteRepository.findAll()).thenReturn(List.of());
        assertNull(fakulteService.findAll());
    }

    @Test
    void findById_whenExists_returnsDto() {
        Fakulte f = new Fakulte();
        f.setFakulteId(3);

        when(fakulteRepository.findById(3)).thenReturn(Optional.of(f));

        var result = fakulteService.findById(3);

        assertNotNull(result);
        assertEquals(3, result.getFakulteId());
    }

    @Test
    void findById_whenNotExists_returnsNull() {
        when(fakulteRepository.findById(9)).thenReturn(Optional.empty());
        assertNull(fakulteService.findById(9));
    }

    @Test
    void save_whenValid_savesAndReturnsDto() {
        DtoFakulte dto = new DtoFakulte();
        dto.setFakulteAdi("Mühendislik");

        Fakulte saved = new Fakulte();
        saved.setFakulteId(10);
        saved.setFakulteAdi("Mühendislik");

        when(fakulteRepository.save(any())).thenReturn(saved);

        var result = fakulteService.save(dto);

        assertNotNull(result);
        assertEquals(10, result.getFakulteId());
        assertEquals("Mühendislik", result.getFakulteAdi());
    }

    @Test
    void update_whenNotExists_returnsNull() {
        DtoFakulte dto = new DtoFakulte();
        dto.setFakulteId(50);

        when(fakulteRepository.findById(50)).thenReturn(Optional.empty());

        assertNull(fakulteService.update(dto));
    }

    @Test
    void update_whenExists_updatesAndReturnsDto() {
        Fakulte existing = new Fakulte();
        existing.setFakulteId(2);
        existing.setFakulteAdi("Eski");

        when(fakulteRepository.findById(2)).thenReturn(Optional.of(existing));
        when(fakulteRepository.save(any())).thenAnswer(a -> a.getArgument(0));

        DtoFakulte dto = new DtoFakulte();
        dto.setFakulteId(2);
        dto.setFakulteAdi("Yeni");

        var result = fakulteService.update(dto);

        assertNotNull(result);
        assertEquals("Yeni", result.getFakulteAdi());
    }

    @Test
    void deleteById_callsRepository() {
        fakulteService.deleteById(7);
        verify(fakulteRepository).deleteById(7);
    }

}
