package com.not_projesi.service.impl;

import com.not_projesi.dto.DtoBolum;
import com.not_projesi.dto.DtoFakulte;
import com.not_projesi.entity.Bolum;
import com.not_projesi.entity.Fakulte;
import com.not_projesi.repository.FakulteRepository;
import com.not_projesi.service.IFakulteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FakulteService implements IFakulteService {

    @Autowired
    private FakulteRepository fakulteRepository;

    @Override
    public List<DtoFakulte> findAll() {
        List<DtoFakulte> dtoFakulteList = new ArrayList<>();
        List<Fakulte> fakulteList = fakulteRepository.findAll();

        if (fakulteList.isEmpty()) {
            return null;
        }

        for (Fakulte fakulte : fakulteList) {
            DtoFakulte dtoFakulte = new DtoFakulte();
            BeanUtils.copyProperties(fakulte, dtoFakulte);
            
            if (fakulte.getBolumList() != null) {
                List<DtoBolum> dtoBolumList = new ArrayList<>();
                for (Bolum bolum : fakulte.getBolumList()) {
                    DtoBolum dtoBolum = new DtoBolum();
                    BeanUtils.copyProperties(bolum, dtoBolum);
                    dtoBolumList.add(dtoBolum);
                }
                dtoFakulte.setBolumList(dtoBolumList);
            }
            
            dtoFakulteList.add(dtoFakulte);
        }

        return dtoFakulteList;
    }

    @Override
    public DtoFakulte findById(Integer fakulteId) {
        Optional<Fakulte> optionalFakulte = fakulteRepository.findById(fakulteId);
        
        if (optionalFakulte.isEmpty()) {
            return null;
        }

        Fakulte fakulte = optionalFakulte.get();
        DtoFakulte dtoFakulte = new DtoFakulte();
        BeanUtils.copyProperties(fakulte, dtoFakulte);

        if (fakulte.getBolumList() != null) {
            List<DtoBolum> dtoBolumList = new ArrayList<>();
            for (Bolum bolum : fakulte.getBolumList()) {
                DtoBolum dtoBolum = new DtoBolum();
                BeanUtils.copyProperties(bolum, dtoBolum);
                dtoBolumList.add(dtoBolum);
            }
            dtoFakulte.setBolumList(dtoBolumList);
        }

        return dtoFakulte;
    }

    @Override
    public DtoFakulte save(DtoFakulte dtoFakulte) {
        Fakulte fakulte = new Fakulte();
        BeanUtils.copyProperties(dtoFakulte, fakulte);

        if (dtoFakulte.getBolumList() != null) {
            List<Bolum> bolumList = new ArrayList<>();
            for (DtoBolum dtoBolum : dtoFakulte.getBolumList()) {
                Bolum bolum = new Bolum();
                BeanUtils.copyProperties(dtoBolum, bolum);
                bolumList.add(bolum);
            }
            fakulte.setBolumList(bolumList);
        }

        Fakulte savedFakulte = fakulteRepository.save(fakulte);
        DtoFakulte savedDtoFakulte = new DtoFakulte();
        BeanUtils.copyProperties(savedFakulte, savedDtoFakulte);
        return savedDtoFakulte;
    }

    @Override
    public void deleteById(Integer fakulteId) {
        fakulteRepository.deleteById(fakulteId);
    }

    @Override
    public DtoFakulte update(DtoFakulte dtoFakulte) {
        Optional<Fakulte> optionalFakulte = fakulteRepository.findById(dtoFakulte.getFakulteId());
        
        if (optionalFakulte.isEmpty()) {
            return null;
        }

        Fakulte existingFakulte = optionalFakulte.get();
        BeanUtils.copyProperties(dtoFakulte, existingFakulte, "fakulteId");

        if (dtoFakulte.getBolumList() != null) {
            List<Bolum> bolumList = new ArrayList<>();
            for (DtoBolum dtoBolum : dtoFakulte.getBolumList()) {
                Bolum bolum = new Bolum();
                BeanUtils.copyProperties(dtoBolum, bolum);
                bolumList.add(bolum);
            }
            existingFakulte.setBolumList(bolumList);
        }

        Fakulte updatedFakulte = fakulteRepository.save(existingFakulte);
        DtoFakulte updatedDtoFakulte = new DtoFakulte();
        BeanUtils.copyProperties(updatedFakulte, updatedDtoFakulte);
        return updatedDtoFakulte;
    }
} 