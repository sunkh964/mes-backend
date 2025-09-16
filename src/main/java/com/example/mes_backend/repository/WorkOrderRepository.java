package com.example.mes_backend.repository;

import com.example.mes_backend.entity.WorkOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WorkOrderRepository
        extends JpaRepository<WorkOrderEntity, Integer>,
                // 조건이 몇 개가 되든 동적으로 조립 가능
                JpaSpecificationExecutor<WorkOrderEntity> {
}
