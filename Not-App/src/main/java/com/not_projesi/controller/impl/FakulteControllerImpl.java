package com.not_projesi.controller.impl;

import com.not_projesi.controller.IFakulteController;
import com.not_projesi.dto.DtoFakulte;
import com.not_projesi.service.IFakulteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rest")
public class FakulteControllerImpl implements IFakulteController {

    @Autowired
    private IFakulteService fakulteService;

    @GetMapping(path = "/fakulte/list")
    @Override
    public List<DtoFakulte> findAll() {
        return fakulteService.findAll();
    }

    @GetMapping(path = "/fakulte/list/{id}")
    @Override
    public DtoFakulte findById(@PathVariable(name = "id", required = true) Integer id) {
        return fakulteService.findById(id);
    }

    @PostMapping(path = "/fakulte/save")
    @Override
    public DtoFakulte save(@RequestBody DtoFakulte dtoFakulte) {
        return fakulteService.save(dtoFakulte);
    }

    @DeleteMapping(path = "/fakulte/delete/{id}")
    @Override
    public void deleteById(@PathVariable(name = "id", required = true) Integer id) {
        fakulteService.deleteById(id);
    }

    @PutMapping(path = "/fakulte/update")
    @Override
    public DtoFakulte update(@RequestBody DtoFakulte dtoFakulte) {
        return fakulteService.update(dtoFakulte);
    }
} 