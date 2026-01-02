package com.not_projesi.controller;

import com.not_projesi.dto.DtoKutuphane;
import java.util.List;

public interface IKutuphaneController {

    public List<DtoKutuphane> findAll();

    public DtoKutuphane findById(Integer id);

    public DtoKutuphane save(DtoKutuphane dtoKutuphane);

    public void deleteById(Integer id);

    public DtoKutuphane update(DtoKutuphane dtoKutuphane);

} 