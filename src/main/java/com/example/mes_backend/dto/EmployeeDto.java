package com.example.mes_backend.dto;

import com.example.mes_backend.entity.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.sql.Date;

@Data
public class EmployeeDto {

    private String employeeId;
    private String employeeNm;
    private Integer departmentId;
    private Integer positionId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    private String phone;
    private String email;
    private String employeeStatus;
    private String role;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // ========= Entity → DTO =========
    public static EmployeeDto fromEntity(Employee entity) {
        EmployeeDto dto = new EmployeeDto();
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setEmployeeNm(entity.getEmployeeNm());
        dto.setDepartmentId(entity.getDepartmentId());
        dto.setPositionId(entity.getPositionId());
        dto.setHireDate(entity.getHireDate());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setEmployeeStatus(entity.getEmployeeStatus());
        dto.setRole(entity.getRole());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    // ========= DTO → Entity =========
    public Employee toEntity() {
        Employee entity = new Employee();
        entity.setEmployeeId(this.employeeId);
        entity.setEmployeeNm(this.employeeNm);
        entity.setDepartmentId(this.departmentId);
        entity.setPositionId(this.positionId);
        entity.setHireDate(this.hireDate);
        entity.setPhone(this.phone);
        entity.setEmail(this.email);
        entity.setEmployeeStatus(this.employeeStatus);
        entity.setRole(this.role);

        // ⚠️ Password는 DTO에서 안 받는 게 일반적이지만,
        // 필요하다면 별도 DTO (예: EmployeeRegisterDto)에 포함시키는 게 좋아요.

        return entity;
    }
}
