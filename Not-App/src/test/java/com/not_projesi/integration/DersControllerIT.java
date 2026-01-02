package com.not_projesi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.not_projesi.dto.DtoDers;
import com.not_projesi.service.IDersService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = NotProjesiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DersControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private IDersService dersService;

    @Test
    void findAll_shouldReturnList() throws Exception {
        DtoDers dto = new DtoDers();
        dto.setDersId(1);
        dto.setDersAdi("Matematik");

        when(dersService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/rest/ders/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dersId").value(1))
                .andExpect(jsonPath("$[0].dersAdi").value("Matematik"));
    }

    @Test
    void findById_shouldReturnDers() throws Exception {
        DtoDers dto = new DtoDers();
        dto.setDersId(2);
        dto.setDersAdi("Fizik");

        when(dersService.findById(2)).thenReturn(dto);

        mockMvc.perform(get("/rest/ders/list/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dersId").value(2))
                .andExpect(jsonPath("$.dersAdi").value("Fizik"));
    }

    @Test
    void save_shouldCreateDers() throws Exception {
        DtoDers dto = new DtoDers();
        dto.setDersAdi("Kimya");

        DtoDers saved = new DtoDers();
        saved.setDersId(10);
        saved.setDersAdi("Kimya");

        when(dersService.save(any(DtoDers.class))).thenReturn(saved);

        mockMvc.perform(
                        post("/rest/ders/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dersId").value(10))
                .andExpect(jsonPath("$.dersAdi").value("Kimya"));
    }

    @Test
    void delete_shouldReturnOk() throws Exception {
        doNothing().when(dersService).deleteById(5);

        mockMvc.perform(delete("/rest/ders/delete/5"))
                .andExpect(status().isOk());

        verify(dersService).deleteById(5);
    }

    @Test
    void update_shouldReturnUpdated() throws Exception {
        DtoDers dto = new DtoDers();
        dto.setDersId(7);
        dto.setDersAdi("Biyoloji");

        when(dersService.update(any(DtoDers.class))).thenReturn(dto);

        mockMvc.perform(
                        put("/rest/ders/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dersId").value(7))
                .andExpect(jsonPath("$.dersAdi").value("Biyoloji"));
    }
}
