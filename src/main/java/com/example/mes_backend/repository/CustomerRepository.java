package com.example.mes_backend.repository;

import com.example.mes_backend.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity,String> {
}
