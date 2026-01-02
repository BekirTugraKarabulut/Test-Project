package com.not_projesi.controller.impl;

import com.not_projesi.controller.IKutuphaneController;
import com.not_projesi.dto.DtoKutuphane;
import com.not_projesi.service.IKutuphaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rest")
public class KutuphaneControllerImpl implements IKutuphaneController {

    @Autowired
    private IKutuphaneService kutuphaneService;

    @GetMapping(path = "/kutuphane/list")
    @Override
    public List<DtoKutuphane> findAll() {
        return kutuphaneService.findAll();
    }

    @GetMapping(path = "/kutuphane/list/{id}")
    @Override
    public DtoKutuphane findById(@PathVariable(name = "id", required = true) Integer id) {
        return kutuphaneService.findById(id);
    }

    @PostMapping(path = "/kutuphane/save")
    @Override
    public DtoKutuphane save(@RequestBody DtoKutuphane dtoKutuphane) {
        return kutuphaneService.save(dtoKutuphane);
    }

    @DeleteMapping(path = "/kutuphane/delete/{id}")
    @Override
    public void deleteById(@PathVariable(name = "id", required = true) Integer id) {
        kutuphaneService.deleteById(id);
    }

    @PutMapping(path = "/kutuphane/update")
    @Override
    public DtoKutuphane update(@RequestBody DtoKutuphane dtoKutuphane) {
        return kutuphaneService.update(dtoKutuphane);
    }
} 