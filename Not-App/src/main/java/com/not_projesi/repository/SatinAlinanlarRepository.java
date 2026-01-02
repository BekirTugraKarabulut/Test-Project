package com.not_projesi.repository;

import com.not_projesi.entity.SatinAlinanlar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SatinAlinanlarRepository extends JpaRepository<SatinAlinanlar, Long> {
    List<SatinAlinanlar> findByOgrenciUsername(String username);
}