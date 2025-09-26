package com.example.mes_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "material_inputs")
public class MaterialInputEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "input_id")
    private Integer inputId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id")
    private WorkResultEntity workResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id")
    private WorkOrderEntity workOrder;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "material_id", nullable = false)
//    private MaterialEntity material;
    @Column(name = "material_id")
    private Integer materialId;   // 단순 FK (ERP material_id 참조)

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit", length = 20)
    private String unit;

    @Column(name = "warehouse", length = 50)
    private String warehouse;

    @Column(name = "location", length = 50)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "input_date")
    private LocalDateTime inputDate;

    @Column(name = "remark", length = 255)
    private String remark;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}