package com.not_projesi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.not_projesi.controller.impl.SepetControllerImpl;
import com.not_projesi.dto.SepetDTO;
import com.not_projesi.service.ISepetService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = NotProjesiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SepetControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ISepetService sepetService;

    @Test
    void sepeteEkle_shouldReturnSaved() throws Exception {
        SepetDTO dto = new SepetDTO();
        dto.setId(1L);
        dto.setUrunAdi("Matematik Notu");

        when(sepetService.sepeteEkle(any(SepetDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/rest/sepet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.urunAdi").value("Matematik Notu"));
    }

    @Test
    void kullaniciSepeti_shouldReturnList() throws Exception {
        SepetDTO dto = new SepetDTO();
        dto.setId(2L);

        when(sepetService.kullaniciSepeti("ayse")).thenReturn(List.of(dto));

        mockMvc.perform(get("/rest/sepet/kullanici/ayse"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2));
    }

    @Test
    void sepettenUrunSil_shouldReturnOk() throws Exception {
        doNothing().when(sepetService).sepettenUrunSil(3L);

        mockMvc.perform(delete("/rest/sepet/3"))
                .andExpect(status().isOk());
    }

    @Test
    void sepetiTemizle_shouldReturnOk() throws Exception {
        doNothing().when(sepetService).sepetiTemizle("ayse");

        mockMvc.perform(delete("/rest/sepet/kullanici/ayse"))
                .andExpect(status().isOk());
    }

    @Test
    void sepettekiUrunleriSatınAl_shouldReturnOk() throws Exception {
        doNothing().when(sepetService).sepettekiUrunleriSatınAl("ayse");

        mockMvc.perform(post("/rest/sepet/satin-al/ayse"))
                .andExpect(status().isOk());
    }
}
