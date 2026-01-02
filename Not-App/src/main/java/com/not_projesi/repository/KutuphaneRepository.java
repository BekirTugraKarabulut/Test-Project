package com.not_projesi.repository;

import com.not_projesi.entity.Kutuphane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KutuphaneRepository extends JpaRepository<Kutuphane, Integer> {
} 