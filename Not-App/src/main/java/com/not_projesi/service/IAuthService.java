package com.not_projesi.service;

import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.dto.RefreshTokenRequest;
import com.not_projesi.jwt.AuthRequest;
import com.not_projesi.jwt.AuthResponse;
import com.not_projesi.jwt.TokenRefreshResponse;

public interface IAuthService {

    public DtoOgrenci register(AuthRequest authRequest);

    public AuthResponse login(AuthRequest authRequest);

    TokenRefreshResponse refreshToken(RefreshTokenRequest request);
}