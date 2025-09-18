package com.example.mes_backend.repository;

import com.example.mes_backend.entity.QualityControlEntity;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QualityControlRepository
        extends JpaRepository<QualityControlEntity, Integer>,
                JpaSpecificationExecutor<QualityControlEntity> {

}