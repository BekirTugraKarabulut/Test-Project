package com.not_projesi.service;

import com.not_projesi.dto.DtoBegen;
import com.not_projesi.dto.DtoBegenUI;

import java.util.List;

public interface IBegenService {
    
    public List<DtoBegen> findAll();
    
    public DtoBegen save(DtoBegenUI dtoBegenUI);
    
    public void deleteById(Integer begenId);

    public List<DtoBegen> getBegenByUsername(String username);
    
    public DtoBegen update(DtoBegen dtoBegen);
} 