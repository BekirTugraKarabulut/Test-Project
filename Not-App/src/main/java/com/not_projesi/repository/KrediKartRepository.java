package com.not_projesi.repository;

import com.not_projesi.entity.KrediKart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KrediKartRepository extends JpaRepository<KrediKart, Long> {
    List<KrediKart> findByOgrenciUsername(String username);
} 