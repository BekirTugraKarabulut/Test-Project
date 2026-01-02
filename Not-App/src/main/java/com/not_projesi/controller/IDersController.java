package com.not_projesi.controller;

import com.not_projesi.dto.DtoDers;
import java.util.List;

public interface IDersController {

    public List<DtoDers> findAll();

    public DtoDers findById(Integer id);

    public DtoDers save(DtoDers dtoDers);

    public void deleteById(Integer id);

    public DtoDers update(DtoDers dtoDers);

} 