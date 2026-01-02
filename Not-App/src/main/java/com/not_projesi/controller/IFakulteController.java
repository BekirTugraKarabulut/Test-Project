package com.not_projesi.controller;

import com.not_projesi.dto.DtoFakulte;
import java.util.List;

public interface IFakulteController {

    public List<DtoFakulte> findAll();

    public DtoFakulte findById(Integer id);

    public DtoFakulte save(DtoFakulte dtoFakulte);

    public void deleteById(Integer id);

    public DtoFakulte update(DtoFakulte dtoFakulte);

} 