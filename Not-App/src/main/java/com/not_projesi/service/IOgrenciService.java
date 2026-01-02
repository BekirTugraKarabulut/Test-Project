package com.not_projesi.service;

import com.not_projesi.dto.DtoOgrenci;

import java.util.List;

public interface IOgrenciService {

    public List<DtoOgrenci> findAll();

    public DtoOgrenci findDtoOgrenciByUsername(String username);

}
