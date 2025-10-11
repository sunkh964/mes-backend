package com.example.mes_backend.repository;

import com.example.mes_backend.entity.ErpPlanReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErpPlanReferenceRepository extends JpaRepository<ErpPlanReference, String> {
}
