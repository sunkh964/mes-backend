package com.example.mes_backend.repository;

import com.example.mes_backend.entity.SalesOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderRepository extends JpaRepository<SalesOrderEntity,String> {
}
