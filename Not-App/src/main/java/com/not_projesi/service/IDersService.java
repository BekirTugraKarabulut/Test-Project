package com.not_projesi.service;

import com.not_projesi.dto.DtoDers;
import java.util.List;

public interface IDersService {
    
    public List<DtoDers> findAll();
    
    public DtoDers findById(Integer dersId);
    
    public DtoDers save(DtoDers dtoDers);
    
    public void deleteById(Integer dersId);
    
    public DtoDers update(DtoDers dtoDers);
} 