package com.not_projesi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.not_projesi.dto.DtoBegen;
import com.not_projesi.dto.DtoBegenUI;
import com.not_projesi.dto.DtoDersNotu;
import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.service.IBegenService;
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
class BegenControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private IBegenService begenService;

    @Test
    void findAll_shouldReturnList() throws Exception {
        DtoBegen dto = new DtoBegen();
        dto.setBegenId(1);
        dto.setOgrenci(new DtoOgrenci());
        dto.setDersNotu(new DtoDersNotu());

        when(begenService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/rest/begen/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].begenId").value(1));
    }

    @Test
    void save_shouldCreateLike() throws Exception {
        DtoBegenUI ui = new DtoBegenUI();
        ui.setOgrenci(new DtoOgrenci());
        ui.setDersNotu(new DtoDersNotu());

        DtoBegen saved = new DtoBegen();
        saved.setBegenId(10);
        saved.setOgrenci(new DtoOgrenci());
        saved.setDersNotu(new DtoDersNotu());

        when(begenService.save(any(DtoBegenUI.class))).thenReturn(saved);

        mockMvc.perform(
                        post("/rest/ekle/begen")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ui))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.begenId").value(10));
    }

    @Test
    void delete_shouldReturnOk() throws Exception {
        doNothing().when(begenService).deleteById(5);

        mockMvc.perform(delete("/rest/begen/delete/5"))
                .andExpect(status().isOk());

        verify(begenService).deleteById(5);
    }

    @Test
    void getByUsername_shouldReturnUserLikes() throws Exception {
        DtoBegen dto = new DtoBegen();
        dto.setBegenId(3);
        dto.setOgrenci(new DtoOgrenci());

        when(begenService.getBegenByUsername("ayse")).thenReturn(List.of(dto));

        mockMvc.perform(get("/rest/begen/ayse"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].begenId").value(3));
    }

    @Test
    void update_shouldReturnUpdated() throws Exception {
        DtoBegen dto = new DtoBegen();
        dto.setBegenId(7);
        dto.setOgrenci(new DtoOgrenci());
        dto.setDersNotu(new DtoDersNotu());

        when(begenService.update(any(DtoBegen.class))).thenReturn(dto);

        mockMvc.perform(
                        put("/rest/begen/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.begenId").value(7));
    }
}
