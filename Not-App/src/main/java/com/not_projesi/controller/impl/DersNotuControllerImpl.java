package com.not_projesi.controller.impl;

import com.not_projesi.controller.IDersNotuController;
import com.not_projesi.dto.DtoDersNotu;
import com.not_projesi.service.IDersNotuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/rest")
public class DersNotuControllerImpl implements IDersNotuController {

    @Autowired
    private IDersNotuService dersNotuService;

    @GetMapping(path = "/ders-notu/list")
    @Override
    public List<DtoDersNotu> findAll() {
        return dersNotuService.findAll();
    }

    @GetMapping(path = "/ders-notu/list/{id}")
    @Override
    public DtoDersNotu findById(@PathVariable(name = "id", required = true) Integer id) {
        return dersNotuService.findById(id);
    }

    @GetMapping(path = "/ders-notlarim/list/{username}")
    @Override
    public List<DtoDersNotu> getByOgrenciUsername(@PathVariable(name = "username" , required = true) String username) {
        return dersNotuService.getByOgrenciUsername(username);
    }

    @GetMapping(path = "/ders-notlari/{dersNotId}")
    @Override
    public DtoDersNotu getById(@PathVariable(name = "dersNotId") Integer dersNotuId) {
        return dersNotuService.getById(dersNotuId);
    }

    @DeleteMapping(path = "/ders-notu/delete/{id}")
    @Override
    public void deleteById(@PathVariable(name = "id", required = true) Integer id) {
        dersNotuService.deleteById(id);
    }

    @PutMapping(path = "/ders-notu/update")
    @Override
    public DtoDersNotu update(@RequestBody DtoDersNotu dtoDersNotu) {
        return dersNotuService.update(dtoDersNotu);
    }

    @PostMapping(path = "/ders-notu/ekle")
    @Override
    public DtoDersNotu saveDersNotu(@RequestParam("username") String username,
                                    @RequestParam("dersNotAdi") String dersNotAdi,
                                    @RequestParam("dersNotIcerik") String dersNotIcerik,
                                    @RequestParam("dersNotFiyat") Integer dersNotFiyat,
                                    @RequestParam("notTuruId") Integer notTuruId,
                                    @RequestParam("pdfFile") MultipartFile pdfFile,
                                    @RequestParam("pdfOnizlemeFile") MultipartFile pdfOnizlemeFile) throws IOException {
        try {

            if (pdfFile.isEmpty() || pdfOnizlemeFile.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dosya eksik");
            }

            if (!pdfFile.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
                return null;
            }

            DtoDersNotu savedDersNotu = dersNotuService.saveDersNotu(
                    username, dersNotAdi, dersNotIcerik, dersNotFiyat,
                    notTuruId, pdfFile, pdfOnizlemeFile);

            return savedDersNotu;

        }catch (IOException e) {
                return null;
        } catch (Exception e) {
                return null;
        }
    }
}
