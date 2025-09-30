package com.example.mes_backend.repository;

import com.example.mes_backend.entity.WorkOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkOrderRepository
        extends JpaRepository<WorkOrderEntity, Integer>,
                // 조건이 몇 개가 되든 동적으로 조립 가능
                JpaSpecificationExecutor<WorkOrderEntity> {

    // WorkCenter + Process 까지 한 번에 로딩
    @Query("SELECT wo FROM WorkOrderEntity wo " +
            "LEFT JOIN FETCH wo.process " +
            "LEFT JOIN FETCH wo.blockPlan " +
            "LEFT JOIN FETCH wo.block " +
            "LEFT JOIN FETCH wo.workCenter wc " +
            "LEFT JOIN FETCH wo.equipment " +
            "LEFT JOIN FETCH wo.employee " +
            "LEFT JOIN FETCH wc.process")
    List<WorkOrderEntity> findAllWithRelations();

}
