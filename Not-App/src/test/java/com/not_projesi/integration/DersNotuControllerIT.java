package com.not_projesi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.not_projesi.dto.DtoDersNotu;
import com.not_projesi.service.IDersNotuService;
import com.not_projesi.starter.NotProjesiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = NotProjesiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DersNotuControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private IDersNotuService dersNotuService;

    @Test
    void findAll_shouldReturnList() throws Exception {
        DtoDersNotu dto = new DtoDersNotu();
        dto.setDersNotId(1);
        dto.setDersNotAdi("Matematik Notu");

        when(dersNotuService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/rest/ders-notu/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dersNotId").value(1))
                .andExpect(jsonPath("$[0].dersNotAdi").value("Matematik Notu"));
    }

    @Test
    void findById_shouldReturnDersNotu() throws Exception {
        DtoDersNotu dto = new DtoDersNotu();
        dto.setDersNotId(2);
        dto.setDersNotAdi("Fizik Notu");

        when(dersNotuService.findById(2)).thenReturn(dto);

        mockMvc.perform(get("/rest/ders-notu/list/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dersNotId").value(2))
                .andExpect(jsonPath("$.dersNotAdi").value("Fizik Notu"));
    }

    @Test
    void saveDersNotu_shouldReturnSaved() throws Exception {
        MockMultipartFile pdfFile = new MockMultipartFile("pdfFile", "not.pdf",
                "application/pdf", "pdf content".getBytes());
        MockMultipartFile pdfPreview = new MockMultipartFile("pdfOnizlemeFile", "preview.pdf",
                "application/pdf", "preview content".getBytes());

        DtoDersNotu saved = new DtoDersNotu();
        saved.setDersNotId(10);
        saved.setDersNotAdi("Kimya Notu");

        when(dersNotuService.saveDersNotu(
                anyString(), anyString(), anyString(),
                anyInt(), anyInt(), any(), any()
        )).thenReturn(saved);

        mockMvc.perform(multipart("/rest/ders-notu/ekle")
                        .file(pdfFile)
                        .file(pdfPreview)
                        .param("username", "ayse")
                        .param("dersNotAdi", "Kimya Notu")
                        .param("dersNotIcerik", "İçerik")
                        .param("dersNotFiyat", "50")
                        .param("notTuruId", "1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dersNotId").value(10))
                .andExpect(jsonPath("$.dersNotAdi").value("Kimya Notu"));
    }

    @Test
    void delete_shouldReturnOk() throws Exception {
        doNothing().when(dersNotuService).deleteById(5);

        mockMvc.perform(delete("/rest/ders-notu/delete/5"))
                .andExpect(status().isOk());

        verify(dersNotuService).deleteById(5);
    }

    @Test
    void update_shouldReturnUpdated() throws Exception {
        DtoDersNotu dto = new DtoDersNotu();
        dto.setDersNotId(7);
        dto.setDersNotAdi("Biyoloji Notu");

        when(dersNotuService.update(any(DtoDersNotu.class))).thenReturn(dto);

        mockMvc.perform(put("/rest/ders-notu/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dersNotId").value(7))
                .andExpect(jsonPath("$.dersNotAdi").value("Biyoloji Notu"));
    }

    @Test
    void getByOgrenciUsername_shouldReturnList() throws Exception {
        DtoDersNotu dto = new DtoDersNotu();
        dto.setDersNotId(3);
        dto.setDersNotAdi("Fizik Notu");

        when(dersNotuService.getByOgrenciUsername("ayse")).thenReturn(List.of(dto));

        mockMvc.perform(get("/rest/ders-notlarim/list/ayse"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dersNotId").value(3))
                .andExpect(jsonPath("$[0].dersNotAdi").value("Fizik Notu"));
    }

    @Test
    void getById_shouldReturnDersNotu() throws Exception {
        DtoDersNotu dto = new DtoDersNotu();
        dto.setDersNotId(4);
        dto.setDersNotAdi("Matematik Notu");

        when(dersNotuService.getById(4)).thenReturn(dto);

        mockMvc.perform(get("/rest/ders-notlari/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dersNotId").value(4))
                .andExpect(jsonPath("$.dersNotAdi").value("Matematik Notu"));
    }
}
