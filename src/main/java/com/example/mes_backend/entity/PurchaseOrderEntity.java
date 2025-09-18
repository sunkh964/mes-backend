package com.example.mes_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "purchase_orders")
public class PurchaseOrderEntity {

    @Id
    @Column(name = "purchase_order_id", nullable = false, length = 20)
    private String purchaseOrderId;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    // 🔽 DB 수정을 하지 않는 조회용 ID 필드
    @Column(name = "supplier_id", insertable = false, updatable = false)
    private Integer supplierId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "total_amount", precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "create_by", nullable = false, length = 50)
    private String createBy;

    @Column(name = "approved_by", length = 50)
    private String approvedBy;

    @Column(name = "approved_date")
    private LocalDateTime approvedDate;

    @Column(name = "remark", length = 255)
    private String remark;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false,
            columnDefinition = "datetime on update current_timestamp")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplier;

    @OneToMany(mappedBy = "purchaseOrder")
    private List<PurchaseDetailEntity> purchaseDetails;
}