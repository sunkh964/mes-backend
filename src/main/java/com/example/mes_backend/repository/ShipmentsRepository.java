package com.example.mes_backend.repository;

import com.example.mes_backend.entity.ShipmentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShipmentsRepository
            extends JpaRepository<ShipmentsEntity, String>,
                    JpaSpecificationExecutor<ShipmentsEntity> {
}
