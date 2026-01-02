package com.not_projesi.repository;

import com.not_projesi.entity.Ogrenci;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilRepository extends JpaRepository<Ogrenci,String> {

    Ogrenci findByUsername(String username);

}
