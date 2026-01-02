package com.not_projesi.controller.impl;

import com.not_projesi.controller.IOgrenciController;
import com.not_projesi.dto.DtoOgrenci;
import com.not_projesi.service.IOgrenciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/rest")
public class OgrenciControllerImpl implements IOgrenciController {

    @Autowired
    private IOgrenciService ogrenciService;

    @GetMapping(path = "/list")
    @Override
    public List<DtoOgrenci> findAll() {
        return ogrenciService.findAll();
    }

    @GetMapping(path = "/list/{username}")
    @Override
    public DtoOgrenci findDtoOgrenciByUsername(@PathVariable(name = "username" , required = true) String username) {
        return ogrenciService.findDtoOgrenciByUsername(username);
    }


}
