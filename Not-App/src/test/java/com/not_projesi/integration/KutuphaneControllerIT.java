package com.not_projesi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.not_projesi.dto.DtoKutuphane;
import com.not_projesi.service.IKutuphaneService;
import com.not_projesi.starter.NotProjesiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = NotProjesiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class KutuphaneControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private IKutuphaneService kutuphaneService;

    @Test
    void findAll_shouldReturnList() throws Exception {
        DtoKutuphane dto = new DtoKutuphane();
        dto.setKutupId(1);
        dto.setKutupAdi("Matematik Kitapları");

        when(kutuphaneService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/rest/kutuphane/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].kutupId").value(1))
                .andExpect(jsonPath("$[0].kutupAdi").value("Matematik Kitapları"));
    }

    @Test
    void findById_shouldReturnItem() throws Exception {
        DtoKutuphane dto = new DtoKutuphane();
        dto.setKutupId(2);
        dto.setKutupAdi("Fizik Kitapları");

        when(kutuphaneService.findById(2)).thenReturn(dto);

        mockMvc.perform(get("/rest/kutuphane/list/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kutupId").value(2))
                .andExpect(jsonPath("$.kutupAdi").value("Fizik Kitapları"));
    }

    @Test
    void save_shouldReturnSaved() throws Exception {
        DtoKutuphane dto = new DtoKutuphane();
        dto.setKutupAdi("Kimya Kitapları");

        DtoKutuphane saved = new DtoKutuphane();
        saved.setKutupId(10);
        saved.setKutupAdi("Kimya Kitapları");

        when(kutuphaneService.save(any(DtoKutuphane.class))).thenReturn(saved);

        mockMvc.perform(post("/rest/kutuphane/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kutupId").value(10))
                .andExpect(jsonPath("$.kutupAdi").value("Kimya Kitapları"));
    }

    @Test
    void update_shouldReturnUpdated() throws Exception {
        DtoKutuphane dto = new DtoKutuphane();
        dto.setKutupId(5);
        dto.setKutupAdi("Biyoloji Kitapları");

        when(kutuphaneService.update(any(DtoKutuphane.class))).thenReturn(dto);

        mockMvc.perform(put("/rest/kutuphane/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kutupId").value(5))
                .andExpect(jsonPath("$.kutupAdi").value("Biyoloji Kitapları"));
    }

    @Test
    void deleteById_shouldReturnOk() throws Exception {
        doNothing().when(kutuphaneService).deleteById(7);

        mockMvc.perform(delete("/rest/kutuphane/delete/7"))
                .andExpect(status().isOk());

        verify(kutuphaneService).deleteById(7);
    }
}
