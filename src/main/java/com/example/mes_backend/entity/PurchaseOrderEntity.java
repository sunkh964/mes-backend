package com.example.mes_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "purchase_orders")
public class PurchaseOrderEntity {
    @Id
    @Column(name = "purchase_order_id", length = 20, nullable = false)
    private String purchaseOrderId;   // PK 복구!

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseDetailEntity> orderDetails = new ArrayList<>();

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @Column(name = "supplier_id", nullable = false)
    private Integer supplierId;

    @Column(name = "status", columnDefinition = "int default 0")
    private Integer status; // 0: 작성, 1: 승인, 2: 입고완료

    @Column(name = "total_amount", precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "create_by", nullable = false, length = 50)
    private String createBy;

    @Column(name = "approved_by", length = 50)
    private String approvedBy;

    @Column(name = "approved_date")
    private LocalDateTime approvedDate;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false,
            columnDefinition = "datetime on update current_timestamp")
    private LocalDateTime updatedAt;


}
