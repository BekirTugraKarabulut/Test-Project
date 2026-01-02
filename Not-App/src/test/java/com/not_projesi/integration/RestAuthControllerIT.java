package com.not_projesi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.dto.RefreshTokenRequest;
import com.not_projesi.jwt.AuthRequest;
import com.not_projesi.jwt.AuthResponse;
import com.not_projesi.jwt.TokenRefreshResponse;
import com.not_projesi.service.IAuthService;
import com.not_projesi.starter.NotProjesiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = NotProjesiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RestAuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IAuthService authService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void register_shouldReturnDtoOgrenci() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setUsername("ali@gmail.com");
        request.setOgrenciSifre("123456");

        DtoOgrenci dtoOgrenci = new DtoOgrenci();
        dtoOgrenci.setUsername("ali@gmail.com");
        dtoOgrenci.setOgrenciAdi("Ali");
        dtoOgrenci.setOgrenciSoyadi("Veli");

        when(authService.register(any(AuthRequest.class))).thenReturn(dtoOgrenci);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("ali@gmail.com"))
                .andExpect(jsonPath("$.ogrenciAdi").value("Ali"))
                .andExpect(jsonPath("$.ogrenciSoyadi").value("Veli"));
    }

    @Test
    void login_shouldReturnAuthResponse() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setUsername("ali@gmail.com");
        request.setOgrenciSifre("123456");

        AuthResponse response = new AuthResponse();
        response.setToken("dummy-access-token");
        response.setRefreshToken("dummy-refresh-token");

        when(authService.login(any(AuthRequest.class))).thenReturn(response);

        mockMvc.perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("dummy-access-token"))
                .andExpect(jsonPath("$.refreshToken").value("dummy-refresh-token"));
    }

    @Test
    void refreshToken_shouldReturnTokenRefreshResponse() throws Exception {
        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken("dummy-refresh-token");

        TokenRefreshResponse response = new TokenRefreshResponse();
        response.setAccessToken("new-access-token");
        response.setRefreshToken("new-refresh-token");

        when(authService.refreshToken(any(RefreshTokenRequest.class))).thenReturn(response);

        mockMvc.perform(post("/refreshtoken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("new-access-token"))
                .andExpect(jsonPath("$.refreshToken").value("new-refresh-token"));
    }

}
