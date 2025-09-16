package com.example.mes_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "block_qc")
@Data
public class BlockQCEntity {

    // 품질검사 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "block_qc_id", nullable = false)
    private Integer blockQCId;

    // 작업지시 (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrderEntity workOrder;

    // 블록 (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id", nullable = false)
    private BlockEntity block;

    // 검사자 (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // 검사 일자
    @Column(name = "inspection_date")
    private LocalDateTime inspectionDate;

    // 검사 결과 (PASS, FAIL, PARTIAL, PENDING)
    @Column(name = "result", length = 20)
    private String result = "PENDING";

    // 합격 수량
    @Column(name = "pass_quantity")
    private Integer passQuantity = 0;

    // 불합격 수량
    @Column(name = "fail_quantity")
    private Integer failQuantity = 0;

    // 불량 유형
    @Column(name = "defect_type", length = 50)
    private String defectType;

    // 비고
    @Column(name = "remark", length = 255)
    private String remark;

    // 생성일시
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 수정일시
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
