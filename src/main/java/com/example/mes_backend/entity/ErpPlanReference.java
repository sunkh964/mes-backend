package com.example.mes_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "erp_plan_references") // DB에 생성될 테이블 이름
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErpPlanReference {
    @Id // 이 필드가 Primary Key임을 나타냅니다.
    private String planId;
}
