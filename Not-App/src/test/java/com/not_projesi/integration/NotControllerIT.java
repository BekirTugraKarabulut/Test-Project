package com.not_projesi.integration;

import com.not_projesi.starter.NotProjesiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = NotProjesiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NotControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void uploadNote_shouldReturnOk() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "example.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "Dummy PDF Content".getBytes()
        );

        mockMvc.perform(multipart("/notes/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Yüklendi:")));
    }


    @Test
    void uploadDersNotu_shouldReturnOk() throws Exception {
        MockMultipartFile pdfFile = new MockMultipartFile(
                "pdfFile",
                "not.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "PDF Content".getBytes()
        );

        MockMultipartFile pdfPreview = new MockMultipartFile(
                "pdfOnizlemeFile",
                "preview.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "Preview Content".getBytes()
        );

        mockMvc.perform(multipart("/rest/ders-notu/ekle")
                        .file(pdfFile)
                        .file(pdfPreview)
                        .param("username", "ayse")
                        .param("dersNotAdi", "Kimya Notu")
                        .param("dersNotIcerik", "İçerik")
                        .param("dersNotFiyat", "50")
                        .param("notTuruId", "1")
                )
                .andExpect(status().isOk());
    }
}
