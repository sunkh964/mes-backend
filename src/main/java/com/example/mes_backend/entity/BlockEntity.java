package com.example.mes_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "blocks")
public class BlockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "block_id", nullable = false)
    private Integer blockId;

    // 선박 ID (외래 키)
    @Column(name = "vessel_id", nullable = false, length = 20)
    private String vesselId;

    // 블록 이름
    @Column(name = "block_nm", nullable = false, length = 50)
    private String blockNm;

    // 블록 종류 (예: 선수, 중앙부, 선미)
    @Column(name = "block_type", length = 20)
    private String blockType;

    // 블록 치수 정보 (예: "2m x 4m x 1.5m")
    @Column(name = "dimensions", length = 50)
    private String dimensions;

    // 무게 (kg)
    @Column(name = "weight")
    private Integer weight;

    // 현재 상태 (예: planned, in-progress, completed)
    @Column(name = "current_status", length = 20)
    private String currentStatus = "planned";

    // 계획 시작일
    @Column(name = "planned_start_date")
    private LocalDate plannedStartDate;

    // 계획 종료일
    @Column(name = "planned_end_date")
    private LocalDate plannedEndDate;

    // 실제 시작일
    @Column(name = "actual_start_date")
    private LocalDate actualStartDate;

    // 실제 종료일
    @Column(name = "actual_end_date")
    private LocalDate actualEndDate;

    // 비고
    @Column(name = "remark", length = 255)
    private String remark;

    // 생성일시
    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    // 수정일시
    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
