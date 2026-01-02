package com.not_projesi.controller;

import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.jwt.AuthRequest;
import com.not_projesi.jwt.AuthResponse;

public interface IRestAuthController {

    public DtoOgrenci register(AuthRequest authRequest);

    public AuthResponse login(AuthRequest authRequest);

}
