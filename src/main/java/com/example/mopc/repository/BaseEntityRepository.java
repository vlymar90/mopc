package com.example.mopc.repository;

import com.example.mopc.entity.BaseClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface BaseEntityRepository<T extends BaseClassEntity
        , E extends Serializable> extends JpaRepository<T, E> {
    List<T> findAllBySerialContaining(String serial);
    List<T> getAllByWorking(String work);
}
