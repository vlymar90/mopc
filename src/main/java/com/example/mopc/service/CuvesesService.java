package com.example.mopc.service;

import com.example.mopc.entity.Apparatus;
import com.example.mopc.entity.Cuveses;
import com.example.mopc.repository.CuvesesRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CuvesesService {
    private final CuvesesRepository cuvesesRepository;
    public CuvesesService(CuvesesRepository cuvesesRepository) {
        this.cuvesesRepository = cuvesesRepository;
    }

    public List<Cuveses> getAll() {
        return cuvesesRepository.findAll();
    }

    public void addElement(Cuveses baseEntity) {
        cuvesesRepository.save(baseEntity);

    }

    public Cuveses getEntity(Long id) {
        return cuvesesRepository.findById(id).orElseThrow();
    }

    public void remove(Long id) {
        cuvesesRepository.deleteById(id);
    }

    public List<Cuveses> getAllLikeSerialNumber(String serialNumber) {
        return cuvesesRepository.findAllBySerialContaining(serialNumber);
    }

    public void save(Cuveses cuveses) {
        cuvesesRepository.save(cuveses);
    }

    public List<Cuveses> getAllMistake() {
        return cuvesesRepository.getAllByWorking("Не исправен");
    }
}
