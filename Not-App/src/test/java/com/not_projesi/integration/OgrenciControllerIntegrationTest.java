package com.not_projesi.integration;

import com.not_projesi.controller.impl.OgrenciControllerImpl;
import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.service.IOgrenciService;
import com.not_projesi.starter.NotProjesiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = NotProjesiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OgrenciControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IOgrenciService ogrenciService;

    @Test
    void findAll_shouldReturnListOfOgrenci() throws Exception {
        DtoOgrenci ogrenci = new DtoOgrenci();
        ogrenci.setUsername("ali");
        ogrenci.setOgrenciAdi("Ali");
        ogrenci.setOgrenciSoyadi("Veli");

        when(ogrenciService.findAll()).thenReturn(List.of(ogrenci));

        mockMvc.perform(get("/rest/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("ali"))
                .andExpect(jsonPath("$[0].ogrenciAdi").value("Ali"))
                .andExpect(jsonPath("$[0].ogrenciSoyadi").value("Veli"));
    }

    @Test
    void findByUsername_shouldReturnOgrenci() throws Exception {
        DtoOgrenci ogrenci = new DtoOgrenci();
        ogrenci.setUsername("ayse");
        ogrenci.setOgrenciAdi("Ayşe");
        ogrenci.setOgrenciSoyadi("Demir");

        when(ogrenciService.findDtoOgrenciByUsername(anyString())).thenReturn(ogrenci);

        mockMvc.perform(get("/rest/list/ayse")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("ayse"))
                .andExpect(jsonPath("$.ogrenciAdi").value("Ayşe"))
                .andExpect(jsonPath("$.ogrenciSoyadi").value("Demir"));
    }
}
