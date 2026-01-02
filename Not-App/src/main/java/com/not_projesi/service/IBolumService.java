package com.not_projesi.service;

import com.not_projesi.dto.DtoBolum;
import java.util.List;

public interface IBolumService {
    
    public List<DtoBolum> findAll();
    
    public DtoBolum findById(Integer bolumId);
    
    public DtoBolum save(DtoBolum dtoBolum);
    
    public void deleteById(Integer bolumId);
    
    public DtoBolum update(DtoBolum dtoBolum);
} 