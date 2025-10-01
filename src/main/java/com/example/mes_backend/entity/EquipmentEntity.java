package com.example.mes_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "equipments")
public class EquipmentEntity {

    @Id
    @Column(name = "equipment_id", length = 20, nullable = false)
    private String equipmentId;

    @Column(name = "equipment_nm", length = 50, nullable = false)
    private String equipmentNm;

    @Column(name = "equipment_type", length = 30)
    private String equipmentType;

    @ManyToOne(fetch = FetchType.LAZY)  // 작업장 정보가 필요한 시점에 데이터를 불러오게 하여 성능을 최적화
    @JoinColumn(name = "work_center_id")
    private WorkCenterEntity workCenter;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "capacity_unit", length = 20)
    private String capacityUnit;   // EA, m, m², TON 등

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "remark", length = 255)
    private String remark;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
