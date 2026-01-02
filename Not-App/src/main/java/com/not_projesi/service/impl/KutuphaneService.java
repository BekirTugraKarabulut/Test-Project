package com.not_projesi.service.impl;

import com.not_projesi.dto.DtoKutuphane;
import com.not_projesi.entity.Kutuphane;
import com.not_projesi.repository.KutuphaneRepository;
import com.not_projesi.service.IKutuphaneService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KutuphaneService implements IKutuphaneService {

    @Autowired
    private KutuphaneRepository kutuphaneRepository;

    @Override
    public List<DtoKutuphane> findAll() {
        List<DtoKutuphane> dtoKutuphaneList = new ArrayList<>();
        List<Kutuphane> kutuphaneList = kutuphaneRepository.findAll();

        if (kutuphaneList.isEmpty()) {
            return null;
        }

        for (Kutuphane kutuphane : kutuphaneList) {
            DtoKutuphane dtoKutuphane = new DtoKutuphane();
            BeanUtils.copyProperties(kutuphane, dtoKutuphane);
            dtoKutuphaneList.add(dtoKutuphane);
        }

        return dtoKutuphaneList;
    }

    @Override
    public DtoKutuphane findById(Integer kutupId) {
        Optional<Kutuphane> optionalKutuphane = kutuphaneRepository.findById(kutupId);
        
        if (optionalKutuphane.isEmpty()) {
            return null;
        }

        Kutuphane kutuphane = optionalKutuphane.get();
        DtoKutuphane dtoKutuphane = new DtoKutuphane();
        BeanUtils.copyProperties(kutuphane, dtoKutuphane);

        return dtoKutuphane;
    }

    @Override
    public DtoKutuphane save(DtoKutuphane dtoKutuphane) {
        Kutuphane kutuphane = new Kutuphane();
        BeanUtils.copyProperties(dtoKutuphane, kutuphane);

        Kutuphane savedKutuphane = kutuphaneRepository.save(kutuphane);
        DtoKutuphane savedDtoKutuphane = new DtoKutuphane();
        BeanUtils.copyProperties(savedKutuphane, savedDtoKutuphane);
        return savedDtoKutuphane;
    }

    @Override
    public void deleteById(Integer kutupId) {
        kutuphaneRepository.deleteById(kutupId);
    }

    @Override
    public DtoKutuphane update(DtoKutuphane dtoKutuphane) {
        Optional<Kutuphane> optionalKutuphane = kutuphaneRepository.findById(dtoKutuphane.getKutupId());
        
        if (optionalKutuphane.isEmpty()) {
            return null;
        }

        Kutuphane existingKutuphane = optionalKutuphane.get();
        BeanUtils.copyProperties(dtoKutuphane, existingKutuphane, "kutupId");

        Kutuphane updatedKutuphane = kutuphaneRepository.save(existingKutuphane);
        DtoKutuphane updatedDtoKutuphane = new DtoKutuphane();
        BeanUtils.copyProperties(updatedKutuphane, updatedDtoKutuphane);
        return updatedDtoKutuphane;
    }
} 