package com.example.mes_backend.repository;

import com.example.mes_backend.entity.BlockEntity;
import com.example.mes_backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<BlockEntity, Integer> {
}
