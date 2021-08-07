package com.example.mopc.service;

import com.example.mopc.entity.Apparatus;
import com.example.mopc.entity.Infusomat;
import com.example.mopc.repository.InfusomatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfusomatService {
    private final InfusomatRepository infusomatRepository;

    public InfusomatService(InfusomatRepository infusomatRepository) {
        this.infusomatRepository = infusomatRepository;
    }

    public List<Infusomat> getAll() {
        return infusomatRepository.findAll();
    }

    public void addElement(Infusomat baseEntity) {
        infusomatRepository.save(baseEntity);

    }

    public Infusomat getEntity(Long id) {
        return infusomatRepository.findById(id).orElseThrow();
    }

    public void remove(Long id) {
        infusomatRepository.deleteById(id);
    }

    public List<Infusomat> getAllLikeSerialNumber(String serialNumber) {
        return infusomatRepository.findAllBySerialContaining(serialNumber);
    }

    public void save(Infusomat apparatus) {
        infusomatRepository.save(apparatus);
    }

    public List<Infusomat> getAllMistake() {
        return infusomatRepository.getAllByWorking("Не исправен");
    }
}
