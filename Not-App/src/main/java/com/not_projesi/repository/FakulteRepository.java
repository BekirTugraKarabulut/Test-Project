package com.not_projesi.repository;

import com.not_projesi.entity.Fakulte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FakulteRepository extends JpaRepository<Fakulte, Integer> {
} 