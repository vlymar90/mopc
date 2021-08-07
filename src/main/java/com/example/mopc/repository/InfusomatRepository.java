package com.example.mopc.repository;

import com.example.mopc.entity.Apparatus;
import com.example.mopc.entity.Infusomat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfusomatRepository extends JpaRepository<Infusomat, Long> {
    List<Infusomat> findAllBySerialContaining(String serial);
    List<Infusomat> getAllByWorking(String work);
}
