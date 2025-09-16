package com.example.mes_backend.repository;

import com.example.mes_backend.entity.BlockQCEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BlockQCRepository
            extends JpaRepository<BlockQCEntity, Integer>,
                    JpaSpecificationExecutor<BlockQCEntity> {
}
