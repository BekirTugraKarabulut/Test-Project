package com.not_projesi.service.impl;

import com.not_projesi.dto.SepetDTO;
import com.not_projesi.dto.SatinAlinanlarDTO;
import com.not_projesi.entity.Sepet;
import com.not_projesi.entity.Ogrenci;
import com.not_projesi.repository.DersNotuRepository;
import com.not_projesi.repository.SepetRepository;
import com.not_projesi.service.ISepetService;
import com.not_projesi.service.ISatinAlinanlarService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SepetServiceImpl implements ISepetService {

    @Autowired
    private SepetRepository sepetRepository;

    @Autowired
    private DersNotuRepository dersNotuRepository;

    @Autowired
    private ISatinAlinanlarService satinAlinanlarService;

    @Override
    public SepetDTO sepeteEkle(SepetDTO sepetDTO) {
        Sepet sepet = new Sepet();
        BeanUtils.copyProperties(sepetDTO, sepet);

        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setUsername(sepetDTO.getOgrenciUsername());
        sepet.setOgrenci(ogrenci);
        sepet.setEklemeTarihi(LocalDateTime.now());

        Sepet savedSepet = sepetRepository.save(sepet);

        SepetDTO responseDTO = new SepetDTO();
        BeanUtils.copyProperties(savedSepet, responseDTO);
        responseDTO.setOgrenciUsername(savedSepet.getOgrenci().getUsername());
        return responseDTO;
    }

    @Override
    public List<SepetDTO> kullaniciSepeti(String username) {
        List<Sepet> sepetList = sepetRepository.findByOgrenciUsername(username);
        List<SepetDTO> dtoList = new ArrayList<>();

        for (Sepet sepet : sepetList) {
            SepetDTO dto = new SepetDTO();
            BeanUtils.copyProperties(sepet, dto);
            dto.setOgrenciUsername(sepet.getOgrenci().getUsername());
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public void sepettenUrunSil(Long id) {
        sepetRepository.deleteById(id);
    }

    @Override
    public void sepetiTemizle(String username) {
        sepetRepository.deleteByOgrenciUsername(username);
    }

    @Override
    @Transactional
    public void sepettekiUrunleriSatınAl(String username) {
        List<Sepet> sepetList = sepetRepository.findByOgrenciUsername(username);

        for (Sepet sepet : sepetList) {

            String pdfName = sepet.getUrunAdi();

            String pdfUrl = dersNotuRepository
                    .findByDersNotAdi(pdfName).getDersNotPdf();

            SatinAlinanlarDTO satinAlinanlarDTO = new SatinAlinanlarDTO();
            satinAlinanlarDTO.setOgrenciUsername(username);
            satinAlinanlarDTO.setUrunAdi(sepet.getUrunAdi());
            satinAlinanlarDTO.setFiyat(sepet.getFiyat());
            satinAlinanlarDTO.setSatinAlmaTarihi(LocalDateTime.now());
            satinAlinanlarDTO.setOdemeDurumu(true);
            satinAlinanlarDTO.setPdfUrl(pdfUrl);

            satinAlinanlarService.satinAlmaEkle(satinAlinanlarDTO);
        }

        sepetiTemizle(username);
    }
}