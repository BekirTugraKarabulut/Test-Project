package com.not_projesi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.not_projesi.controller.impl.FakulteControllerImpl;
import com.not_projesi.dto.DtoFakulte;
import com.not_projesi.service.IFakulteService;
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

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = NotProjesiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FakulteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private IFakulteService fakulteService;

    @Test
    void findAll_shouldReturnList() throws Exception {
        DtoFakulte dto = new DtoFakulte();
        dto.setFakulteId(1);
        dto.setFakulteAdi("Mühendislik");

        when(fakulteService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/rest/fakulte/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fakulteId").value(1))
                .andExpect(jsonPath("$[0].fakulteAdi").value("Mühendislik"));
    }

    @Test
    void findById_shouldReturnFakulte() throws Exception {
        DtoFakulte dto = new DtoFakulte();
        dto.setFakulteId(2);
        dto.setFakulteAdi("Fen-Edebiyat");

        when(fakulteService.findById(2)).thenReturn(dto);

        mockMvc.perform(get("/rest/fakulte/list/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fakulteId").value(2))
                .andExpect(jsonPath("$.fakulteAdi").value("Fen-Edebiyat"));
    }

    @Test
    void save_shouldReturnSaved() throws Exception {
        DtoFakulte dto = new DtoFakulte();
        dto.setFakulteAdi("İktisat");

        DtoFakulte saved = new DtoFakulte();
        saved.setFakulteId(10);
        saved.setFakulteAdi("İktisat");

        when(fakulteService.save(any(DtoFakulte.class))).thenReturn(saved);

        mockMvc.perform(post("/rest/fakulte/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fakulteId").value(10))
                .andExpect(jsonPath("$.fakulteAdi").value("İktisat"));
    }

    @Test
    void update_shouldReturnUpdated() throws Exception {
        DtoFakulte dto = new DtoFakulte();
        dto.setFakulteId(5);
        dto.setFakulteAdi("Tıp Fakültesi");

        when(fakulteService.update(any(DtoFakulte.class))).thenReturn(dto);

        mockMvc.perform(put("/rest/fakulte/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fakulteId").value(5))
                .andExpect(jsonPath("$.fakulteAdi").value("Tıp Fakültesi"));
    }

    @Test
    void delete_shouldReturnOk() throws Exception {
        doNothing().when(fakulteService).deleteById(3);

        mockMvc.perform(delete("/rest/fakulte/delete/3"))
                .andExpect(status().isOk());

        verify(fakulteService).deleteById(3);
    }
}
