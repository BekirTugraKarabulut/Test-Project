package com.not_projesi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.not_projesi.controller.impl.BolumControllerImpl;
import com.not_projesi.dto.DtoBolum;
import com.not_projesi.service.IBolumService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = NotProjesiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BolumControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private IBolumService bolumService;

    @Test
    void findAll_shouldReturnList() throws Exception {
        DtoBolum dto = new DtoBolum();
        dto.setBolumId(1);
        dto.setBolumAdi("Bilgisayar Mühendisliği");

        when(bolumService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/rest/bolum/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bolumId").value(1))
                .andExpect(jsonPath("$[0].bolumAdi").value("Bilgisayar Mühendisliği"));
    }

    @Test
    void findById_shouldReturnBolum() throws Exception {
        DtoBolum dto = new DtoBolum();
        dto.setBolumId(2);
        dto.setBolumAdi("Elektrik-Elektronik");

        when(bolumService.findById(2)).thenReturn(dto);

        mockMvc.perform(get("/rest/bolum/list/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bolumId").value(2))
                .andExpect(jsonPath("$.bolumAdi").value("Elektrik-Elektronik"));
    }

    @Test
    void save_shouldCreateBolum() throws Exception {
        DtoBolum input = new DtoBolum();
        input.setBolumAdi("Makine Mühendisliği");

        DtoBolum saved = new DtoBolum();
        saved.setBolumId(10);
        saved.setBolumAdi("Makine Mühendisliği");

        when(bolumService.save(any(DtoBolum.class))).thenReturn(saved);

        mockMvc.perform(
                        post("/rest/bolum/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bolumId").value(10))
                .andExpect(jsonPath("$.bolumAdi").value("Makine Mühendisliği"));
    }

    @Test
    void update_shouldReturnUpdated() throws Exception {
        DtoBolum dto = new DtoBolum();
        dto.setBolumId(5);
        dto.setBolumAdi("Güncellenmiş Bölüm");

        when(bolumService.update(any(DtoBolum.class))).thenReturn(dto);

        mockMvc.perform(
                        put("/rest/bolum/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bolumId").value(5))
                .andExpect(jsonPath("$.bolumAdi").value("Güncellenmiş Bölüm"));
    }

    @Test
    void delete_shouldReturnOk() throws Exception {
        doNothing().when(bolumService).deleteById(3);

        mockMvc.perform(delete("/rest/bolum/delete/3"))
                .andExpect(status().isOk());

        verify(bolumService).deleteById(3);
    }
}
