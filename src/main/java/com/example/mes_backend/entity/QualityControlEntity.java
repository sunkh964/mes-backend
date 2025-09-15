package com.example.mes_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "quality_control")
public class QualityControlEntity {
    // 품질검사 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qc_id" , nullable = false)
    private Integer qcId;

    //발주 ID
    @Column(name = "purchase_order_id", length = 20, nullable = false)
    private String purchaseOrderId;

    //발주 상세 ID
    @Column(name = "order_detail_id", nullable = false)
    private Integer orderDetailId;

    // 작업지시 ID
    @Column(name = "work_order_id")
    private Integer workOrderId;

    // 자재 ID
    @Column(name = "material_id", nullable = false)
    private Integer materialId;

    // 검사자 ID
    @Column(name = "inspector_id", nullable = false, length = 20)
    private String inspectorId;

    // 검사 수행일
    @CreationTimestamp
    @Column(name = "inspection_date", nullable = false)
    private LocalDateTime inspectionDate;

    //검사 결과
    @Column(name = "result", nullable = false, length = 20)
    private String result;

    // 합격 수량
    @Column(name = "pass_quantity", columnDefinition = "int default 0")
    private Integer passQuantity;

    // 불합격 수량
    @Column(name = "fail_quantity", columnDefinition = "int default 0")
    private Integer failQuantity;

    // 불량 유형
    @Column(name = "defect_type", length = 50)
    private String defectType;

    // 비고
    @Column(name = "remark", length = 255)
    private String remark;

    // 생성일
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    // 수정일
    @CreationTimestamp
    @Column(name = "updated_at", insertable = false, nullable = false)
    private LocalDateTime updatedAt;
}