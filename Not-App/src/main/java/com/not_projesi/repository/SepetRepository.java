package com.not_projesi.repository;

import com.not_projesi.entity.Sepet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SepetRepository extends JpaRepository<Sepet, Long> {
    List<Sepet> findByOgrenciUsername(String username);

    void deleteByOgrenciUsername(String username);
}