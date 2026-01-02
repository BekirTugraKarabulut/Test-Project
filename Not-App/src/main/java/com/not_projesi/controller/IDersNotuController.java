package com.not_projesi.controller;

import com.not_projesi.dto.DtoDersNotu;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IDersNotuController {

    public List<DtoDersNotu> findAll();

    public DtoDersNotu findById(Integer id);

    public List<DtoDersNotu> getByOgrenciUsername(String username);

    public DtoDersNotu getById(Integer dersNotuId);

    public void deleteById(Integer id);

    public DtoDersNotu update(DtoDersNotu dtoDersNotu);

    public DtoDersNotu saveDersNotu(
            String username,
            String dersNotAdi,
            String dersNotIcerik,
            Integer dersNotFiyat,
            Integer notTuruId,
            MultipartFile pdfFile,
            MultipartFile pdfOnizlemeFile) throws IOException;

} 