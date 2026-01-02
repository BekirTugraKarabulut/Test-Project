package com.not_projesi.controller.impl;

import com.not_projesi.controller.IRestAuthController;
import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.dto.RefreshTokenRequest;
import com.not_projesi.jwt.AuthRequest;
import com.not_projesi.jwt.AuthResponse;
import com.not_projesi.jwt.TokenRefreshResponse;
import com.not_projesi.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthControllerImpl implements IRestAuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping(path = "/register")
    @Override
    public DtoOgrenci register(@RequestBody AuthRequest authRequest) {
        return authService.register(authRequest);
    }

    @PostMapping(path = "/authenticate")
    @Override
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

}
