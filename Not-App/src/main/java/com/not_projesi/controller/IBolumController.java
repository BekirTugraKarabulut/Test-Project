package com.not_projesi.controller;

import com.not_projesi.dto.DtoBolum;
import java.util.List;

public interface IBolumController {

    public List<DtoBolum> findAll();

    public DtoBolum findById(Integer id);

    public DtoBolum save(DtoBolum dtoBolum);

    public void deleteById(Integer id);

    public DtoBolum update(DtoBolum dtoBolum);

} 