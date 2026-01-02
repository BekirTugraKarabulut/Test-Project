package com.not_projesi.controller;

import com.not_projesi.dto.DtoOgrenci;

import java.util.List;

public interface IOgrenciController {

    public List<DtoOgrenci> findAll();

    public DtoOgrenci findDtoOgrenciByUsername(String username);

}
