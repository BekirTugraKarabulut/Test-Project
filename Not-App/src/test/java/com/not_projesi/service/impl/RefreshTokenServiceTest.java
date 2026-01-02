package com.not_projesi.service.impl;

import com.not_projesi.entity.Ogrenci;
import com.not_projesi.entity.RefreshToken;
import com.not_projesi.exception.TokenRefreshException;
import com.not_projesi.repository.OgrenciRepository;
import com.not_projesi.repository.RefreshTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RefreshTokenServiceTest {

    @Mock
    RefreshTokenRepository refreshTokenRepository;

    @Mock
    OgrenciRepository ogrenciRepository;

    @InjectMocks
    RefreshTokenService refreshTokenService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(
                refreshTokenService,
                "refreshTokenDurationMs",
                60000L
        );
    }

    @Test
    void createRefreshToken_varsaGunceller() {

        RefreshToken existing = new RefreshToken();
        existing.setToken("old-token");
        existing.setExpiryDate(Instant.now().minusSeconds(10));

        when(refreshTokenRepository.findByOgrenci_Username("ali"))
                .thenReturn(Optional.of(existing));

        when(refreshTokenRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RefreshToken result = refreshTokenService.createRefreshToken("ali");

        assertNotNull(result.getToken());
        assertTrue(result.getExpiryDate().isAfter(Instant.now()));
        verify(refreshTokenRepository).save(existing);
    }

    @Test
    void createRefreshToken_yoksaYeniOlusturur() {

        Ogrenci ogr = new Ogrenci();
        ogr.setUsername("ali");

        when(refreshTokenRepository.findByOgrenci_Username("ali"))
                .thenReturn(Optional.empty());

        when(ogrenciRepository.findByUsername("ali"))
                .thenReturn(Optional.of(ogr));

        when(refreshTokenRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RefreshToken result = refreshTokenService.createRefreshToken("ali");

        assertEquals(ogr, result.getOgrenci());
        assertNotNull(result.getToken());
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }


    @Test
    void verifyExpiration_suresiDolmussaExceptionFırlatırVeSiler() {

        RefreshToken token = new RefreshToken();
        token.setToken("t1");
        token.setExpiryDate(Instant.now().minusSeconds(5));

        assertThrows(
                TokenRefreshException.class,
                () -> refreshTokenService.verifyExpiration(token)
        );

        verify(refreshTokenRepository).delete(token);
    }

    @Test
    void verifyExpiration_gecerliyseTokeniDondurur() {

        RefreshToken token = new RefreshToken();
        token.setToken("t1");
        token.setExpiryDate(Instant.now().plusSeconds(60));

        RefreshToken result = refreshTokenService.verifyExpiration(token);

        assertEquals(token, result);
        verify(refreshTokenRepository, never()).delete(any());
    }

    @Test
    void deleteByOgrenciUsername_dogruSekildeSilmeYapar() {

        Ogrenci ogr = new Ogrenci();
        ogr.setUsername("ali");

        when(ogrenciRepository.findByUsername("ali"))
                .thenReturn(Optional.of(ogr));

        when(refreshTokenRepository.deleteByOgrenci(ogr))
                .thenReturn(1);

        int result = refreshTokenService.deleteByOgrenciUsername("ali");

        assertEquals(1, result);
        verify(refreshTokenRepository).deleteByOgrenci(ogr);
    }
}
