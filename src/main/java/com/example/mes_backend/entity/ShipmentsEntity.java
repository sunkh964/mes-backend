package com.example.mes_backend.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "shipments")
public class ShipmentsEntity {

    @Id
    @Column(name = "shipment_id", length = 20)
    private String shipmentId;

    // 수주 (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_order_id", nullable = false)
    private SalesOrderEntity salesOrder;

    // 고객 (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    // 선박 (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vessel_id", nullable = false)
    private VesselEntity vessel;

    // 출하예정일
    @Column(name = "planned_ship_date")
    private LocalDateTime plannedShipDate;

    // 실제 출하일
    @Column(name = "actual_ship_date")
    private LocalDateTime actualShipDate;

    // 상태 (0: 계획, 1: 출하, 2: 인도완료, 3: 취소)
    @Column(name = "status", columnDefinition = "INT DEFAULT 0")
    private Integer status = 0;

    // 등록 담당자
    @Column(name = "created_by", nullable = false, length = 20)
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

    // 생성일시
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 수정일시
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

