package com.not_projesi.integration;

import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.dto.DtoOgrenciUI;
import com.not_projesi.service.ProfilService;
import com.not_projesi.starter.NotProjesiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = NotProjesiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProfilControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfilService profilService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void ogrenciBilgileriByUsername_shouldReturnOgrenci() throws Exception {
        DtoOgrenci ogrenci = new DtoOgrenci();
        ogrenci.setUsername("ali");
        ogrenci.setOgrenciAdi("Ali");
        ogrenci.setOgrenciSoyadi("Veli");

        when(profilService.ogrenciBilgileriByUsername(anyString())).thenReturn(ogrenci);

        mockMvc.perform(get("/rest/profil-bilgi/ali")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("ali"))
                .andExpect(jsonPath("$.ogrenciAdi").value("Ali"))
                .andExpect(jsonPath("$.ogrenciSoyadi").value("Veli"));
    }

    @Test
    void guncelleOgrenciByUsername_shouldReturnUpdatedOgrenci() throws Exception {
        DtoOgrenciUI dtoUI = new DtoOgrenciUI();
        dtoUI.setOgrenciAdi("Ayşe");
        dtoUI.setOgrenciSoyadi("Demir");

        DtoOgrenci updated = new DtoOgrenci();
        updated.setUsername("ayse");
        updated.setOgrenciAdi("Ayşe");
        updated.setOgrenciSoyadi("Demir");

        when(profilService.guncelleOgrenciByUsername(anyString(), any(DtoOgrenciUI.class))).thenReturn(updated);

        mockMvc.perform(put("/rest/guncelle/profil-bilgi/ayse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoUI)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("ayse"))
                .andExpect(jsonPath("$.ogrenciAdi").value("Ayşe"))
                .andExpect(jsonPath("$.ogrenciSoyadi").value("Demir"));
    }
}
