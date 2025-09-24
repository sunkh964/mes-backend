package com.example.mes_backend.repository;

import com.example.mes_backend.entity.PurchaseOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrderEntity,String> {
}
