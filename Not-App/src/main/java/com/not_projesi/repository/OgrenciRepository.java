package com.not_projesi.repository;

import com.not_projesi.entity.Ogrenci;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OgrenciRepository extends JpaRepository<Ogrenci, String> {

    Optional<Ogrenci> findByUsername(String username);

    @Query("SELECT o FROM Ogrenci o WHERE LOWER(o.ogrenciEmail) = LOWER(:email)")
    Optional<Ogrenci> findByOgrenciEmailIgnoreCase(@Param("email") String email);

    Optional<Ogrenci> findByResetToken(String resetToken);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);

}
