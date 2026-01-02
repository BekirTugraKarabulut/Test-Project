package com.not_projesi.controller;

import com.not_projesi.dto.DtoBegen;
import com.not_projesi.dto.DtoBegenUI;

import java.util.List;

public interface IBegenController {
    
    public List<DtoBegen> findAll();

    public DtoBegen save(DtoBegenUI dtoBegenUI);

    public void deleteById(Integer id);

    public List<DtoBegen> getBegenByUsername(String username);

    public DtoBegen update(DtoBegen dtoBegen);

} 