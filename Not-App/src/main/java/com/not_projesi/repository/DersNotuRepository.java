package com.not_projesi.repository;

import com.not_projesi.entity.DersNotu;
import com.not_projesi.entity.Ogrenci;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DersNotuRepository extends JpaRepository<DersNotu, Integer> {

    List<DersNotu> findByOgrenci(Ogrenci ogrenci);

    DersNotu findByDersNotId(Integer dersNotId);

    DersNotu findByDersNotAdi(String dersNotAdi);





} 