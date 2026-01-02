package com.not_projesi.controller.impl;

import com.not_projesi.controller.IBegenController;
import com.not_projesi.dto.DtoBegen;
import com.not_projesi.dto.DtoBegenUI;
import com.not_projesi.service.IBegenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rest")
public class BegenControllerImpl implements IBegenController {

    @Autowired
    private IBegenService begenService;

    @GetMapping(path = "/begen/list")
    @Override
    public List<DtoBegen> findAll() {
        return begenService.findAll();
    }

    @PostMapping(path = "/ekle/begen")
    @Override
    public DtoBegen save(@RequestBody DtoBegenUI dtoBegenUI) {
        return begenService.save(dtoBegenUI);
    }

    @DeleteMapping(path = "/begen/delete/{id}")
    @Override
    public void deleteById(@PathVariable(name = "id", required = true) Integer id) {
        begenService.deleteById(id);
    }

    @Override
    @GetMapping(path = "/begen/{username}")
    public List<DtoBegen> getBegenByUsername(@PathVariable(name = "username", required = true) String username) {
        return begenService.getBegenByUsername(username);
    }

    @PutMapping(path = "/begen/update")
    @Override
    public DtoBegen update(@RequestBody DtoBegen dtoBegen) {
        return begenService.update(dtoBegen);
    }

}