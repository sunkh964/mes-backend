//package com.example.mes_backend.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Data
//@Entity
//@Table(name = "materials")
//public class MaterialEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "material_id")
//    private Integer materialId;

//@Column(name = "material_code", nullable = false, unique = true, length = 50)
//private String materialCode;   // 비즈니스 자재코드 (ex: MT-STEEL-001)
//
//    @Column(name = "material_nm", nullable = false, length = 50)
//    private String materialNm;
//
//    @Column(name = "category", length = 20)
//    private String category;
//
//    @Column(name = "specification", nullable = false, length = 200)
//    private String specification;
//
//    @Column(name = "unit", nullable = false, length = 20)
//    private String unit;
//
//    @Column(name = "unit_price", nullable = false)
//    private Double unitPrice;
//
//    @Column(name = "current_price", precision = 10, scale = 2)
//    private BigDecimal currentPrice;
//
//    @Column(name = "min_stock_quantity", nullable = false)
//    private Integer minStockQuantity;
//
//    @Column(name = "max_stock_quantity", nullable = false)
//    private Integer maxStockQuantity;
//
//    @Column(name = "lead_time")
//    private Integer leadTime;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "supplier_id")
//    private SupplierEntity supplier;
//
//    @Column(name = "last_purchase_date")
//    private LocalDate lastPurchaseDate;
//
//    @Column(name = "status")
//    private Integer status = 0;
//
//    @Column(name = "warehouse", nullable = false, length = 20)
//    private String warehouse;
//
//    @Column(name = "location", nullable = false, length = 50)
//    private String location;
//
//    @Column(name = "remark", length = 255)
//    private String remark;
//
//    @CreationTimestamp
//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//}