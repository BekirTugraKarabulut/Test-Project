package com.not_projesi.repository;

import com.not_projesi.entity.Begen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BegenRepository extends JpaRepository<Begen, Integer> {

} 