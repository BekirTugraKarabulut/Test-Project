package com.not_projesi.service.impl;
import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.dto.RefreshTokenRequest;
import com.not_projesi.entity.Bolum;
import com.not_projesi.entity.Ogrenci;
import com.not_projesi.entity.RefreshToken;
import com.not_projesi.exception.TokenRefreshException;
import com.not_projesi.jwt.AuthRequest;
import com.not_projesi.jwt.AuthResponse;
import com.not_projesi.jwt.JwtService;
import com.not_projesi.jwt.TokenRefreshResponse;
import com.not_projesi.repository.OgrenciRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    OgrenciRepository ogrenciRepository;

    @Mock
    JwtService jwtService;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Mock
    AuthenticationProvider authenticationProvider;

    @Mock
    RefreshTokenService refreshTokenService;

    @InjectMocks
    AuthServiceImpl authService;

    @Test
    void register_basariylaKayitYapar() {

        AuthRequest req = new AuthRequest();
        req.setUsername("ali");
        req.setOgrenciAdi("Ali");
        req.setOgrenciSoyadi("Yılmaz");
        req.setOgrenciEmail("ali@test.com");
        req.setOgrenciSifre("123");
        req.setBolumId(5);

        when(ogrenciRepository.findByUsername("ali")).thenReturn(Optional.empty());
        when(ogrenciRepository.findByOgrenciEmailIgnoreCase("ali@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123")).thenReturn("ENCODED");

        Ogrenci saved = new Ogrenci();
        saved.setOgrenciEmail("ali@test.com");
        saved.setUsername("ali");
        saved.setBolumList(new Bolum());

        when(ogrenciRepository.save(any(Ogrenci.class))).thenReturn(saved);

        DtoOgrenci result = authService.register(req);

        assertEquals("ali", result.getUsername());
        verify(ogrenciRepository).save(any(Ogrenci.class));
    }

    @Test
    void register_kullaniciAdiVarIseHataFırlatır() {

        AuthRequest req = new AuthRequest();
        req.setUsername("ali");

        when(ogrenciRepository.findByUsername("ali"))
                .thenReturn(Optional.of(new Ogrenci()));

        ResponseStatusException ex =
                assertThrows(ResponseStatusException.class, () -> authService.register(req));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    void register_emailVarIseHataFırlatır() {

        AuthRequest req = new AuthRequest();
        req.setUsername("ali");
        req.setOgrenciEmail("ali@test.com");

        when(ogrenciRepository.findByUsername("ali")).thenReturn(Optional.empty());
        when(ogrenciRepository.findByOgrenciEmailIgnoreCase("ali@test.com"))
                .thenReturn(Optional.of(new Ogrenci()));

        assertThrows(ResponseStatusException.class, () -> authService.register(req));
    }

    @Test
    void login_dogruBilgiIleTokenDonderir() {

        AuthRequest req = new AuthRequest();
        req.setUsername("ali");
        req.setOgrenciSifre("123");

        Ogrenci ogr = new Ogrenci();
        ogr.setUsername("ali");

        when(ogrenciRepository.findByUsername("ali")).thenReturn(Optional.of(ogr));
        when(jwtService.generateToken(ogr)).thenReturn("JWT");
        RefreshToken r = new RefreshToken();
        r.setToken("REFRESH");
        when(refreshTokenService.createRefreshToken("ali")).thenReturn(r);

        AuthResponse res = authService.login(req);

        assertEquals("JWT", res.getToken());
        assertEquals("REFRESH", res.getRefreshToken());
        verify(authenticationProvider).authenticate(any());
    }

    @Test
    void login_kullaniciBulunamazsaHataFırlatır() {

        AuthRequest req = new AuthRequest();
        req.setUsername("ali");
        req.setOgrenciSifre("123");

        when(ogrenciRepository.findByUsername("ali")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.login(req));
    }

    @Test
    void refreshToken_gecerliyseYeniTokenDoner() {

        RefreshTokenRequest req = new RefreshTokenRequest();
        req.setRefreshToken("AAA");

        Ogrenci ogr = new Ogrenci();
        RefreshToken rt = new RefreshToken();
        rt.setOgrenci(ogr);

        when(refreshTokenService.findByToken("AAA")).thenReturn(Optional.of(rt));
        when(refreshTokenService.verifyExpiration(rt)).thenReturn(rt);
        when(jwtService.generateToken(ogr)).thenReturn("NEW_TOKEN");

        TokenRefreshResponse res = authService.refreshToken(req);

        assertEquals("NEW_TOKEN", res.getAccessToken());
        assertEquals("AAA", res.getRefreshToken());
    }

    @Test
    void refreshToken_veritabanindaYoksaExceptionAtar() {

        RefreshTokenRequest req = new RefreshTokenRequest();
        req.setRefreshToken("INVALID");

        when(refreshTokenService.findByToken("INVALID")).thenReturn(Optional.empty());

        assertThrows(TokenRefreshException.class, () -> authService.refreshToken(req));
    }
}