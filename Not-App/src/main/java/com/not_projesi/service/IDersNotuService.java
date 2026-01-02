package com.not_projesi.service;

import com.not_projesi.dto.DtoDersNotu;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IDersNotuService {
    
    public List<DtoDersNotu> findAll();
    
    public DtoDersNotu findById(Integer dersNotuId);

    public List<DtoDersNotu> getByOgrenciUsername(String username);
    
    public void deleteById(Integer dersNotuId);

    public DtoDersNotu getById(Integer dersNotuId);
    
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