package com.not_projesi.controller.impl;

import com.not_projesi.controller.IBolumController;
import com.not_projesi.dto.DtoBolum;
import com.not_projesi.service.IBolumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rest")
public class BolumControllerImpl implements IBolumController {


    @Autowired
    private IBolumService bolumService;

    @GetMapping(path = "/bolum/list")
    @Override
    public List<DtoBolum> findAll() {
        return bolumService.findAll();
    }

    @GetMapping(path = "/bolum/list/{id}")
    @Override
    public DtoBolum findById(@PathVariable(name = "id", required = true) Integer id) {
        return bolumService.findById(id);
    }

    @PostMapping(path = "/bolum/save")
    @Override
    public DtoBolum save(@RequestBody DtoBolum dtoBolum) {
        return bolumService.save(dtoBolum);
    }

    @DeleteMapping(path = "/bolum/delete/{id}")
    @Override
    public void deleteById(@PathVariable(name = "id", required = true) Integer id) {
        bolumService.deleteById(id);
    }

    @PutMapping(path = "/bolum/update")
    @Override
    public DtoBolum update(@RequestBody DtoBolum dtoBolum) {
        return bolumService.update(dtoBolum);
    }
} 