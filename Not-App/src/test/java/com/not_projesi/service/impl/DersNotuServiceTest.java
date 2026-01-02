package com.not_projesi.service.impl;

import com.not_projesi.dto.DtoDersNotu;
import com.not_projesi.entity.DersNotu;
import com.not_projesi.entity.NotTuru;
import com.not_projesi.entity.Ogrenci;
import com.not_projesi.repository.DersNotuRepository;
import com.not_projesi.repository.NotTuruRepository;
import com.not_projesi.repository.OgrenciRepository;
import com.not_projesi.service.IFileStorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DersNotuServiceTest {

    @Mock
    private DersNotuRepository dersNotuRepository;

    @Mock
    private OgrenciRepository ogrenciRepository;

    @Mock
    private NotTuruRepository notTuruRepository;

    @Mock
    private IFileStorageService fileStorageService;

    @InjectMocks
    private DersNotuService dersNotuService;

    @Test
    void findAll_whenListExists_returnsDtos() {
        Ogrenci ogrenci = new Ogrenci();
        NotTuru notTuru = new NotTuru();

        DersNotu dersNotu = new DersNotu();
        dersNotu.setDersNotId(5);
        dersNotu.setOgrenci(ogrenci);
        dersNotu.setNotTuru(notTuru);

        when(dersNotuRepository.findAll()).thenReturn(List.of(dersNotu));

        var result = dersNotuService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getDersNotId());
    }

    @Test
    void findAll_whenEmpty_returnsNull() {
        when(dersNotuRepository.findAll()).thenReturn(List.of());
        assertNull(dersNotuService.findAll());
    }


    @Test
    void findById_whenExists_returnsDto() {
        DersNotu dersNotu = new DersNotu();
        dersNotu.setDersNotId(7);

        when(dersNotuRepository.findById(7)).thenReturn(Optional.of(dersNotu));

        var result = dersNotuService.findById(7);

        assertNotNull(result);
        assertEquals(7, result.getDersNotId());
    }

    @Test
    void findById_whenNotExists_returnsNull() {
        when(dersNotuRepository.findById(99)).thenReturn(Optional.empty());
        assertNull(dersNotuService.findById(99));
    }


    @Test
    void getByOgrenciUsername_whenUserNotFound_returnsEmptyList() {
        when(ogrenciRepository.findByUsername("ali")).thenReturn(Optional.empty());
        assertTrue(dersNotuService.getByOgrenciUsername("ali").isEmpty());
    }

    @Test
    void getByOgrenciUsername_whenFound_returnsList() {
        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setUsername("ali");

        DersNotu dn = new DersNotu();
        dn.setDersNotId(1);
        dn.setOgrenci(ogrenci);
        dn.setNotTuru(new NotTuru());

        when(ogrenciRepository.findByUsername("ali")).thenReturn(Optional.of(ogrenci));
        when(dersNotuRepository.findByOgrenci(ogrenci)).thenReturn(List.of(dn));

        var result = dersNotuService.getByOgrenciUsername("ali");

        assertEquals(1, result.size());
    }


    @Test
    void update_whenNotExists_returnsNull() {
        var dto = new DtoDersNotu();
        dto.setDersNotId(100);

        when(dersNotuRepository.findById(100)).thenReturn(Optional.empty());

        assertNull(dersNotuService.update(dto));
    }

    @Test
    void update_whenExists_updatesAndReturnsDto() {
        DersNotu existing = new DersNotu();
        existing.setDersNotId(3);

        when(dersNotuRepository.findById(3)).thenReturn(Optional.of(existing));
        when(dersNotuRepository.save(any())).thenAnswer(a -> a.getArgument(0));

        var dto = new DtoDersNotu();
        dto.setDersNotId(3);
        dto.setDersNotAdi("Yeni Ad");

        var result = dersNotuService.update(dto);

        assertNotNull(result);
        assertEquals("Yeni Ad", result.getDersNotAdi());
    }


    @Test
    void saveDersNotu_success() throws IOException {
        Ogrenci ogrenci = new Ogrenci();
        NotTuru notTuru = new NotTuru();

        when(ogrenciRepository.findByUsername("veli")).thenReturn(Optional.of(ogrenci));
        when(notTuruRepository.findById(2)).thenReturn(Optional.of(notTuru));

        when(fileStorageService.storeFile(any(), eq("ders-notlari")))
                .thenReturn("path/pdf");
        when(fileStorageService.storeFile(any(), eq("ders-notlari-onizleme")))
                .thenReturn("path/preview");

        when(dersNotuRepository.save(any())).thenAnswer(a -> {
            DersNotu dn = a.getArgument(0);
            dn.setDersNotId(50);
            return dn;
        });

        MultipartFile pdf = mock(MultipartFile.class);
        MultipartFile preview = mock(MultipartFile.class);

        DtoDersNotu result = dersNotuService.saveDersNotu(
                "veli", "Mat 1", "icerik", 10, 2, pdf, preview
        );

        assertNotNull(result);
        assertEquals(50, result.getDersNotId());
        assertEquals("Mat 1", result.getDersNotAdi());
    }

    @Test
    void saveDersNotu_whenUserMissing_throws() {
        when(ogrenciRepository.findByUsername("x")).thenReturn(Optional.empty());

        MultipartFile f = mock(MultipartFile.class);

        assertThrows(RuntimeException.class, () ->
                dersNotuService.saveDersNotu("x","a","b",1,1,f,f)
        );
    }

    @Test
    void saveDersNotu_whenNotTuruMissing_throws() {
        Ogrenci o = new Ogrenci();
        when(ogrenciRepository.findByUsername("x")).thenReturn(Optional.of(o));
        when(notTuruRepository.findById(9)).thenReturn(Optional.empty());

        MultipartFile f = mock(MultipartFile.class);

        assertThrows(RuntimeException.class, () ->
                dersNotuService.saveDersNotu("x","a","b",1,9,f,f)
        );
    }
}
