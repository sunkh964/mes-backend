package com.example.mes_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales_orders")
@Data
public class SalesOrderEntity {

    // 수주번호 (예: SO2025-00001)
    @Id
    @Column(name = "sales_order_id", length = 20, nullable = false)
    private String salesOrderId;

    // 수주 일자 (DATE)
    @Column(name = "order_date")
    private LocalDate orderDate;

    // 고객
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    // 선박
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vessel_id", nullable = false)
    private VesselEntity vessel;

    // 고객 발주 번호 (예: PO-2025-00123)
    @Column(name = "customer_po_no", length = 30)
    private String customerPoNo;

    // 통화코드 (기본값: KRW)
    @Column(name = "currency_code", length = 3, nullable = false)
    private String currencyCode = "KRW";

    // 상태: 0 작성, 1 승인, 2 부분출하, 3 출하완료, 4 취소
    @Column(name = "status")
    private Integer status = 0;

    // 총 금액
    @Column(name = "total_amount", precision = 15, scale = 2)
    private BigDecimal totalAmount;

    // 등록 담당
    @Column(name = "created_by", length = 20, nullable = false)
    private String createdBy;

    // 승인 일시
    @Column(name = "approved_date")
    private LocalDateTime approvedDate;

    // 승인 담당자
    @Column(name = "approved_by", length = 20)
    private String approvedBy;

    // 비고
    @Column(name = "remark", length = 255)
    private String remark;

    // 생성일(자동 셋팅)
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 수정일(자동 셋팅)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}