package com.example.mopc.service;

import com.example.mopc.entity.Apparatus;
import com.example.mopc.repository.ApparatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApparatusService  {
    @Autowired
    private ApparatusRepository apparatusRepository;

    public ApparatusService(ApparatusRepository apparatusRepository) {
        this.apparatusRepository = apparatusRepository;
    }

    public List<Apparatus> getAll() {
        return apparatusRepository.findAll();
    }

    public void addElement(Apparatus baseEntity) {
        apparatusRepository.save(baseEntity);

    }

    public Apparatus getEntity(Long id) {
        return apparatusRepository.findById(id).orElseThrow();
    }

    public void remove(Long id) {
        apparatusRepository.deleteById(id);
    }

    public List<Apparatus> getAllLikeSerialNumber(String serialNumber) {
        return apparatusRepository.findAllBySerialContaining(serialNumber);
    }

    public void save(Apparatus apparatus) {
        apparatusRepository.save(apparatus);
    }

    public List<Apparatus> getAllMistake() {
        return apparatusRepository.getAllByWorking("Не исправен");
    }
}
