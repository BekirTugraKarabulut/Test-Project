package com.not_projesi.service;

import com.not_projesi.dto.DtoKutuphane;
import java.util.List;

public interface IKutuphaneService {
    
    public List<DtoKutuphane> findAll();
    
    public DtoKutuphane findById(Integer kutupId);
    
    public DtoKutuphane save(DtoKutuphane dtoKutuphane);
    
    public void deleteById(Integer kutupId);
    
    public DtoKutuphane update(DtoKutuphane dtoKutuphane);
} 