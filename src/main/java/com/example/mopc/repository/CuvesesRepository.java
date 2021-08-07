package com.example.mopc.repository;

import com.example.mopc.entity.Apparatus;
import com.example.mopc.entity.Cuveses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuvesesRepository extends JpaRepository<Cuveses, Long> {
    List<Cuveses> findAllBySerialContaining(String serial);
    List<Cuveses> getAllByWorking(String work);
}
