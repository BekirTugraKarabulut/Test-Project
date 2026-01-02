package com.not_projesi.repository;

import com.not_projesi.entity.Ogrenci;
import com.not_projesi.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByOgrenci_Username(String username);

    @Modifying
    int deleteByOgrenci(Ogrenci ogrenci);

}