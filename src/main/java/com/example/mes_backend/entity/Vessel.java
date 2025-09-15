package com.example.mes_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vessels")
@Data
public class Vessel {

    @Id
    @Column(name = "vessel_id", length = 20, nullable = false)
    private String vesselId;

    @Column(name = "vessel_nm", length = 50, nullable = false)
    private String vesselName;

    @Column(name = "vessel_type", length = 20, nullable = false)
    private String vesselType;

    @Column(name = "status", nullable = false)
    private Integer status = 0; // 0: 계획, 1: 진행, 2: 완료

    @Column(name = "vessel_length", precision = 6, scale = 2)
    private BigDecimal vesselLength;

    @Column(name = "vessel_beam", precision = 5, scale = 2)
    private BigDecimal vesselBeam;

    @Column(name = "vessel_depth", precision = 5, scale = 2)
    private BigDecimal vesselDepth;

    @Column(name = "cargo_capacity", length = 20)
    private String cargoCapacity;

    @Column(name = "engine_spec", length = 100)
    private String engineSpec;

    @Column(name = "total_weight", precision = 10, scale = 2)
    private BigDecimal totalWeight;

    @Column(name = "actual_delivery_date")
    private LocalDate actualDeliveryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "remark", length = 255)
    private String remark;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
