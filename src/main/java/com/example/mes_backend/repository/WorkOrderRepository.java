package com.example.mes_backend.repository;

import com.example.mes_backend.entity.WorkOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderRepository extends JpaRepository<WorkOrderEntity, Integer> {
}
