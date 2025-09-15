package com.example.mes_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "employees")
public class Employee {

    // 사원 ID (기본키, 유니크)
    @Id
    @Column(name = "employee_id", length = 20, nullable = false, unique = true)
    private String employeeId;

    // 사원 이름
    @Column(name = "employee_nm", length = 10, nullable = false)
    private String employeeNm;

    @Column(name = "department_id", nullable = false)
    private Integer departmentId;

    @Column(name = "position_id", nullable = false)
    private Integer positionId;

    // 입사일
    @Column(name = "hire_date", nullable = false)
    private Date hireDate;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    // 사원 상태
    @Column(name = "employee_status", length = 10)
    private String employeeStatus;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    // 역할(권한)
    @Column(name = "role", length = 255, nullable = false)
    private String role; // 예: "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_WORKER"

    // 생성 일시 (자동 설정, DB에서 CURRENT_TIMESTAMP 사용)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    // 수정 일시 (자동 갱신, DB에서 ON UPDATE CURRENT_TIMESTAMP 사용)
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
