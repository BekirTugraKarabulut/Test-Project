package com.not_projesi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.not_projesi.dto.KrediKartDTO;
import com.not_projesi.service.IKrediKartService;
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
class KrediKartControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private IKrediKartService krediKartService;

    @Test
    void findAll_shouldReturnList() throws Exception {
        KrediKartDTO dto = new KrediKartDTO();
        dto.setId(1L);
        dto.setKartNo("1234567890123456");

        when(krediKartService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/rest/kredi-kart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].kartNo").value("1234567890123456"));
    }

    @Test
    void findByUsername_shouldReturnList() throws Exception {
        KrediKartDTO dto = new KrediKartDTO();
        dto.setId(2L);
        dto.setKartNo("9876543210987654");

        when(krediKartService.findByUsername("ayse")).thenReturn(List.of(dto));

        mockMvc.perform(get("/rest/kredi-kart/ayse"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].kartNo").value("9876543210987654"));
    }

    @Test
    void kullaniciKartlari_shouldReturnList() throws Exception {
        KrediKartDTO dto = new KrediKartDTO();
        dto.setId(3L);
        dto.setKartNo("1111222233334444");

        when(krediKartService.kullaniciKartlari("ali")).thenReturn(List.of(dto));

        mockMvc.perform(get("/rest/kredi-kart/kullanici/ali"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3))
                .andExpect(jsonPath("$[0].kartNo").value("1111222233334444"));
    }

    @Test
    void kartEkle_shouldReturnSaved() throws Exception {
        KrediKartDTO dto = new KrediKartDTO();
        dto.setKartNo("5555666677778888");

        KrediKartDTO saved = new KrediKartDTO();
        saved.setId(10L);
        saved.setKartNo("5555666677778888");

        when(krediKartService.kartEkle(any(KrediKartDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/rest/kredi-kart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.kartNo").value("5555666677778888"));
    }

    @Test
    void kartSil_shouldReturnOk() throws Exception {
        doNothing().when(krediKartService).kartSil(5L);

        mockMvc.perform(delete("/rest/kredi-kart/5"))
                .andExpect(status().isOk());

        verify(krediKartService).kartSil(5L);
    }
}
