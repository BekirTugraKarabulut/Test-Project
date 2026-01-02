package com.not_projesi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.not_projesi.dto.SatinAlinanlarDTO;
import com.not_projesi.service.ISatinAlinanlarService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = NotProjesiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SatinAlinanlarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ISatinAlinanlarService satinAlinanlarService;

    @Test
    void findAll_shouldReturnList() throws Exception {
        SatinAlinanlarDTO dto = new SatinAlinanlarDTO();
        dto.setId(1L);
        dto.setUrunAdi("Matematik Notu");

        when(satinAlinanlarService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/rest/satin-alinanlar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].urunAdi").value("Matematik Notu"));
    }

    @Test
    void satinAlmaEkle_shouldReturnSaved() throws Exception {
        SatinAlinanlarDTO dto = new SatinAlinanlarDTO();
        dto.setId(10L);
        dto.setUrunAdi("Fizik Notu");

        when(satinAlinanlarService.satinAlmaEkle(any(SatinAlinanlarDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/rest/satin-alinanlar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.urunAdi").value("Fizik Notu"));
    }

    @Test
    void kullaniciSatinAlmalari_shouldReturnList() throws Exception {
        SatinAlinanlarDTO dto = new SatinAlinanlarDTO();
        dto.setId(2L);

        when(satinAlinanlarService.kullaniciSatinAlmalari("ayse")).thenReturn(List.of(dto));

        mockMvc.perform(get("/rest/satin-alinanlar/kullanici/ayse"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2));
    }

    @Test
    void odemeYap_shouldReturnDto() throws Exception {
        SatinAlinanlarDTO dto = new SatinAlinanlarDTO();
        dto.setId(5L);

        when(satinAlinanlarService.odemeYap(5L)).thenReturn(dto);

        mockMvc.perform(post("/rest/satin-alinanlar/odeme/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    void findByUsername_shouldReturnList() throws Exception {
        SatinAlinanlarDTO dto = new SatinAlinanlarDTO();
        dto.setId(3L);

        when(satinAlinanlarService.findByUsername("ayse")).thenReturn(List.of(dto));

        mockMvc.perform(get("/rest/satin-alinanlar/ayse"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3));
    }

    @Test
    void satinAlinanUrunuSil_shouldReturnOk() throws Exception {
        doNothing().when(satinAlinanlarService).satinAlinanUrunuSil(7L);

        mockMvc.perform(delete("/rest/satin-alinanlar/7"))
                .andExpect(status().isOk());
    }
}
