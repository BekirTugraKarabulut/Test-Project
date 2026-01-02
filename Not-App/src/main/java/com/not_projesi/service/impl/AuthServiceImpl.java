package com.not_projesi.service.impl;

import com.not_projesi.dto.DtoBolum;
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
import com.not_projesi.service.IAuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private OgrenciRepository ogrenciRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private com.not_projesi.service.impl.RefreshTokenService refreshTokenService;

    @Override
    public DtoOgrenci register(AuthRequest authRequest) {

        DtoOgrenci dtoOgrenci = new DtoOgrenci();
        Ogrenci ogrenci = new Ogrenci();

        ogrenci.setUsername(authRequest.getUsername());
        ogrenci.setOgrenciAdi(authRequest.getOgrenciAdi());
        ogrenci.setOgrenciSoyadi(authRequest.getOgrenciSoyadi());
        ogrenci.setOgrenciEmail(authRequest.getOgrenciEmail());
        ogrenci.setOgrenciSifre(bCryptPasswordEncoder.encode(authRequest.getOgrenciSifre()));

        Optional<Ogrenci> dbOgrenciUsername = ogrenciRepository.findByUsername(authRequest.getUsername());

        if (dbOgrenciUsername.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bu kullanıcı adı zaten alınmış !");
        }

        Optional<Ogrenci> dbOgrenciEmail = ogrenciRepository.findByOgrenciEmailIgnoreCase(authRequest.getOgrenciEmail());

        if(dbOgrenciEmail.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bu email daha önce kullanılmış !");
        }

        Bolum  bolum = new Bolum();
        bolum.setBolumId(authRequest.getBolumId());

        ogrenci.setBolumList(bolum);

        DtoBolum dtoBolum = new DtoBolum();
        BeanUtils.copyProperties(bolum, dtoBolum);

        Ogrenci dbOgrenci = ogrenciRepository.save(ogrenci);

        BeanUtils.copyProperties(dbOgrenci, dtoOgrenci);
        dtoOgrenci.setDtoBolum(dtoBolum);

        return dtoOgrenci;
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        try {
            UsernamePasswordAuthenticationToken auth
                    = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getOgrenciSifre());
            authenticationProvider.authenticate(auth);

            Optional<Ogrenci> optionalOgrenci = ogrenciRepository.findByUsername(authRequest.getUsername());

            if (optionalOgrenci.isPresent()) {
                Ogrenci ogrenci = optionalOgrenci.get();
                String token = jwtService.generateToken(ogrenci);

                RefreshToken refreshToken = refreshTokenService.createRefreshToken(ogrenci.getUsername());

                return new AuthResponse(token, refreshToken.getToken());
            } else {
                throw new RuntimeException("Öğrenci bulunamadı");
            }
        } catch (Exception e) {
            System.out.println("Kullanıcı adı veya şifre hatalıdır! " + e.getMessage());
            throw new RuntimeException("Kullanıcı adı veya şifre hatalıdır!");
        }
    }

    @Override
    public TokenRefreshResponse refreshToken(RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getOgrenci)
                .map(ogrenci -> {
                    String token = jwtService.generateToken(ogrenci);
                    return new TokenRefreshResponse(token, requestRefreshToken);
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh Token Veri Tabanında Yoktur !"));
    }
}