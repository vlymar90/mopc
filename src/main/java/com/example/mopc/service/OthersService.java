package com.example.mopc.service;

import com.example.mopc.entity.Apparatus;
import com.example.mopc.entity.Others;
import com.example.mopc.repository.OthersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OthersService  {
    private final OthersRepository othersRepository;

    public OthersService(OthersRepository othersRepository) {
        this.othersRepository = othersRepository;
    }

    public List<Others> getAll() {
        return othersRepository.findAll();
    }

    public void addElement(Others baseEntity) {
        othersRepository.save(baseEntity);

    }

    public Others getEntity(Long id) {
        return othersRepository.findById(id).orElseThrow();
    }

    public void remove(Long id) {
        othersRepository.deleteById(id);
    }

    public List<Others> getAllLikeSerialNumber(String serialNumber) {
        return othersRepository.findAllBySerialContaining(serialNumber);
    }

    public void save(Others apparatus) {
        othersRepository.save(apparatus);
    }

    public List<Others> getAllMistake() {
        return othersRepository.getAllByWorking("Не исправен");
    }
}
