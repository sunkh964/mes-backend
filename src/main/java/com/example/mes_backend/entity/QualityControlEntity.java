package com.example.mes_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    // 발주 수량은 다른 페이지 참조

    // 발주 (PurchaseOrderEntity와 관계 매핑)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrderEntity purchaseOrderId;

    // 발주 상세 (PurchaseOrderDetailEntity와 관계 매핑)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_detail_id", nullable = false)
    private PurchaseDetailEntity orderDetailId;

    // 작업지시 (WorkOrderEntity와 관계 매핑, NULL 허용)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id")
    private WorkOrderEntity workOrderId;

    // 자재 (MaterialEntity와 관계 매핑)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private MaterialEntity materialId;

    // 검사자 (Employee Entity와 관계 매핑)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspector_id", nullable = false)
    private Employee inspectorId;

    // 검사 수행일
    @CreationTimestamp
    @Column(name = "inspection_date", nullable = false)
    private LocalDateTime inspectionDate;

    //검사 결과
    @Column(name = "result", nullable = false, length = 20)
    private String result = "PENDING";

    // 합격 수량
    @Column(name = "pass_quantity", columnDefinition = "int default 0")
    private Integer passQuantity=0;

    // 불합격 수량
    @Column(name = "fail_quantity", columnDefinition = "int default 0")
    private Integer failQuantity=0;

    // 불량 유형
    @Column(name = "defect_type", length = 50)
    private String defectType;

    // 비고
    @Column(name = "remark", length = 255)
    private String remark;

    // 생성일
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 수정일
    @UpdateTimestamp
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;
}