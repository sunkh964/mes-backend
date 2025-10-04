package com.example.mes_backend.repository;

import com.example.mes_backend.entity.BlockPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlockPlanRepository
        extends  JpaRepository<BlockPlanEntity, Integer>,
                // 조건이 몇 개가 되든 동적으로 조립 가능
                 JpaSpecificationExecutor<BlockPlanEntity>{

    // BlockPlanRepository.java
    @Query("SELECT bp FROM BlockPlanEntity bp " +
            "JOIN FETCH bp.blockEntity " +
            "JOIN FETCH bp.process " +
            "WHERE bp.planId = :planId")
    List<BlockPlanEntity> findByPlanIdWithJoins(@Param("planId") String planId);

}
