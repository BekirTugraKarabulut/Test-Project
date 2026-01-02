package com.not_projesi.service;

import com.not_projesi.dto.DtoFakulte;
import java.util.List;

public interface IFakulteService {
    
    public List<DtoFakulte> findAll();
    
    public DtoFakulte findById(Integer fakulteId);
    
    public DtoFakulte save(DtoFakulte dtoFakulte);
    
    public void deleteById(Integer fakulteId);
    
    public DtoFakulte update(DtoFakulte dtoFakulte);
} 