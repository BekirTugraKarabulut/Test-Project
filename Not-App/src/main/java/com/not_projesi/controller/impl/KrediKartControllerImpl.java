package com.not_projesi.controller.impl;

import com.not_projesi.controller.IKrediKartController;
import com.not_projesi.dto.KrediKartDTO;
import com.not_projesi.service.IKrediKartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rest/kredi-kart")
public class KrediKartControllerImpl implements IKrediKartController {

    @Autowired
    private IKrediKartService krediKartService;

    @Override
    @PostMapping
    public KrediKartDTO kartEkle(@RequestBody KrediKartDTO krediKartDTO) {
        return krediKartService.kartEkle(krediKartDTO);
    }

    @Override
    @GetMapping("/kullanici/{username}")
    public List<KrediKartDTO> kullaniciKartlari(@PathVariable String username) {
        return krediKartService.kullaniciKartlari(username);
    }

    @Override
    @DeleteMapping("/{id}")
    public void kartSil(@PathVariable Long id) {
        krediKartService.kartSil(id);
    }

    @Override
    @GetMapping("/{username}")
    public List<KrediKartDTO> findByUsername(@PathVariable String username) {
        return krediKartService.findByUsername(username);
    }

    @Override
    @GetMapping
    public List<KrediKartDTO> findAll() {
        return krediKartService.findAll();
    }
} 