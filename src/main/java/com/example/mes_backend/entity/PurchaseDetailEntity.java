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

    //purchase_order_id 또 적을 필요없음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false) // FK는 여기만!
    private PurchaseOrderEntity purchaseOrder;

    @Column(name = "material_id", nullable = false)
    private Integer materialId;

    @Column(name = "order_quantity", nullable = false)
    private Integer orderQuantity;

    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "amount", precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "received_quantity", columnDefinition = "int default 0")
    private Integer receivedQuantity;

    @Column(name = "status", columnDefinition = "int default 0")
    private Integer status; // 0:대기 1:부분입고 2:완료

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false,
            columnDefinition = "datetime on update current_timestamp")
    private LocalDateTime updatedAt;

    @Column(name = "inspected_quantity", columnDefinition = "int default 0")
    private Integer inspectedQuantity;

    @Column(name = "inspection_status", columnDefinition = "int default 0")
    private Integer inspectionStatus;

}
