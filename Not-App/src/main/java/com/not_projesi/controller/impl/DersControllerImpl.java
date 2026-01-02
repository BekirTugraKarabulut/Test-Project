package com.not_projesi.controller.impl;

import com.not_projesi.controller.IDersController;
import com.not_projesi.dto.DtoDers;
import com.not_projesi.service.IDersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rest")
public class DersControllerImpl implements IDersController {

    @Autowired
    private IDersService dersService;

    @GetMapping(path = "/ders/list")
    @Override
    public List<DtoDers> findAll() {
        return dersService.findAll();
    }

    @GetMapping(path = "/ders/list/{id}")
    @Override
    public DtoDers findById(@PathVariable(name = "id", required = true) Integer id) {
        return dersService.findById(id);
    }

    @PostMapping(path = "/ders/save")
    @Override
    public DtoDers save(@RequestBody DtoDers dtoDers) {
        return dersService.save(dtoDers);
    }

    @DeleteMapping(path = "/ders/delete/{id}")
    @Override
    public void deleteById(@PathVariable(name = "id", required = true) Integer id) {
        dersService.deleteById(id);
    }

    @PutMapping(path = "/ders/update")
    @Override
    public DtoDers update(@RequestBody DtoDers dtoDers) {
        return dersService.update(dtoDers);
    }
} 