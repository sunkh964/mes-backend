package com.example.mes_backend.repository;

import com.example.mes_backend.entity.QualityControlEntity;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface QualityControlRepository
        extends JpaRepository<QualityControlEntity, Integer>,
                JpaSpecificationExecutor<QualityControlEntity> {
    List<QualityControlEntity> findByResultNot(String result);
}