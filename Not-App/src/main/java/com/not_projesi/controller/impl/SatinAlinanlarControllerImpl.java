package com.not_projesi.controller.impl;

import com.not_projesi.controller.ISatinAlinanlarController;
import com.not_projesi.dto.SatinAlinanlarDTO;
import com.not_projesi.service.ISatinAlinanlarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rest/satin-alinanlar")
public class SatinAlinanlarControllerImpl implements ISatinAlinanlarController {

    @Autowired
    private ISatinAlinanlarService satinAlinanlarService;

    @Override
    @PostMapping
    public SatinAlinanlarDTO satinAlmaEkle(@RequestBody SatinAlinanlarDTO satinAlinanlarDTO) {
        return satinAlinanlarService.satinAlmaEkle(satinAlinanlarDTO);
    }

    @Override
    @GetMapping("/kullanici/{username}")
    public List<SatinAlinanlarDTO> kullaniciSatinAlmalari(@PathVariable String username) {
        return satinAlinanlarService.kullaniciSatinAlmalari(username);
    }

    @Override
    @PostMapping("/odeme/{id}")
    public SatinAlinanlarDTO odemeYap(@PathVariable Long id) {
        return satinAlinanlarService.odemeYap(id);
    }

    @Override
    @GetMapping("/{username}")
    public List<SatinAlinanlarDTO> findByUsername(@PathVariable String username) {
        return satinAlinanlarService.findByUsername(username);
    }

    @Override
    @GetMapping
    public List<SatinAlinanlarDTO> findAll() {
        return satinAlinanlarService.findAll();
    }

    @DeleteMapping("/{id}")
    public void satinAlinanUrunuSil(@PathVariable Long id) {
        satinAlinanlarService.satinAlinanUrunuSil(id);
    }
}