package com.example.mes_backend.repository;

import com.example.mes_backend.entity.VesselEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VesselRepository extends JpaRepository<VesselEntity, String> {
}
