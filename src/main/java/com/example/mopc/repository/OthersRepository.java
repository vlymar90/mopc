package com.example.mopc.repository;

import com.example.mopc.entity.Apparatus;
import com.example.mopc.entity.Infusomat;
import com.example.mopc.entity.Others;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OthersRepository extends BaseEntityRepository<Others,Long> {

}
