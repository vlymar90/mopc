package com.example.mopc.repository;

import com.example.mopc.entity.Apparatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApparatusRepository extends JpaRepository<Apparatus, Long> {
    List<Apparatus> findAllBySerialContaining(String serial);
    List<Apparatus> getAllByWorking(String work);
}