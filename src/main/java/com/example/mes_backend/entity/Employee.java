package com.example.mes_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "employee_id", length = 20, nullable = false, unique = true)
    private String employeeId;

    @Column(name = "employee_nm", length = 10, nullable = false)
    private String employeeNm;

    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "position_id")
    private Integer positionId;

    @Column(name = "hire_date")
    private Date hireDate;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    @Column(name = "employee_status", length = 10)
    private String employeeStatus;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // 예: "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_WORKER"

    @Builder
    public Employee(String employeeId, String employeeNm, Integer departmentId, Integer positionId, Date hireDate, String phone, String email, String employeeStatus, String password, String role) {
        this.employeeId = employeeId;
        this.employeeNm = employeeNm;
        this.departmentId = departmentId;
        this.positionId = positionId;
        this.hireDate = hireDate;
        this.phone = phone;
        this.email = email;
        this.employeeStatus = employeeStatus;
        this.password = password;
        this.role = role;
    }
}
