package com.not_projesi.controller.impl;

import com.not_projesi.dto.SepetDTO;
import com.not_projesi.service.ISepetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rest/sepet")
public class SepetControllerImpl {

    @Autowired
    private ISepetService sepetService;

    @PostMapping
    public SepetDTO sepeteEkle(@RequestBody SepetDTO sepetDTO) {
        return sepetService.sepeteEkle(sepetDTO);
    }

    @GetMapping("/kullanici/{username}")
    public List<SepetDTO> kullaniciSepeti(@PathVariable String username) {
        return sepetService.kullaniciSepeti(username);
    }

    @DeleteMapping("/{id}")
    public void sepettenUrunSil(@PathVariable Long id) {
        sepetService.sepettenUrunSil(id);
    }

    @DeleteMapping("/kullanici/{username}")
    public void sepetiTemizle(@PathVariable String username) {
        sepetService.sepetiTemizle(username);
    }

    @PostMapping("/satin-al/{username}")
    public void sepettekiUrunleriSatınAl(@PathVariable String username) {
        sepetService.sepettekiUrunleriSatınAl(username);
    }
}