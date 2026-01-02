package com.not_projesi.repository;

import com.not_projesi.entity.NotTuru;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotTuruRepository extends JpaRepository<NotTuru,Integer> {

    Optional<NotTuru> findById(Integer notId);

    Optional<NotTuru> findByNotAdi(String notAdi);

    List<NotTuru> findAll();

}
