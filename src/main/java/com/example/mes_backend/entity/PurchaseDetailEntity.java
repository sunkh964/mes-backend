package com.example.mes_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "purchase_order_details")
public class PurchaseDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id", nullable = false)
    private Integer orderDetailId;

    // 🔽 관계의 주인은 PurchaseOrderEntity 객체이므로, 이 컬럼은 DB에 값을 쓰지 않도록 설정
    @Column(name = "purchase_order_id", nullable = false, length = 20, insertable = false, updatable = false)
    private String purchaseOrderId;

    // 🔽 관계의 주인은 MaterialEntity 객체이므로, 이 컬럼은 DB에 값을 쓰지 않도록 설정
    @Column(name = "material_id", nullable = false, insertable = false, updatable = false)
    private Integer materialId;

    @Column(name = "order_quantity", nullable = false)
    private Integer orderQuantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "amount", precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "received_quantity")
    private Integer receivedQuantity;

    @Column(name = "inspected_quantity")
    private Integer inspectedQuantity;

    @Column(name = "inspection_status")
    private Integer inspectionStatus;

    @Column(name = "status")
    private Integer status;

    @Column(name = "remark", length = 255)
    private String remark;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false,
            columnDefinition = "datetime on update current_timestamp")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrderEntity purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private MaterialEntity material;
}