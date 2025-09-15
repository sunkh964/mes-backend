package com.example.mes_backend.repository;

import com.example.mes_backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findByEmployeeId(String employeeId);
}
