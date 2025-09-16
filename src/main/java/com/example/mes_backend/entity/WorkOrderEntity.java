package com.example.mes_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "work_orders")
@Data
public class WorkOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_order_id", nullable = false)
    private Integer workOrderId;

    // 공정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id")
    private ProcessEntity process;

    // 블록 계획
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_plan_id")
    private BlockPlanEntity blockPlan;

    // 블록
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id")
    private BlockEntity block;

    // 작업장
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_center_id")
    private WorkCenterEntity workCenter;

    // 설비
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private EquipmentEntity equipment;

    // 작업자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    // 지시사항
    @Column(name = "instruction", length = 50)
    private String instruction;

    // 생산 예정 수량
    @Column(name = "quantity_to_produce", nullable = false)
    private Integer quantityToProduce;

    // 생산 완료 수량
    @Column(name = "quantity_produced")
    private Integer quantityProduced = 0;

    // 계획 시작일
    @Column(name = "planned_start_time")
    private LocalDateTime plannedStartTime;

    // 계획 종료일
    @Column(name = "planned_end_time")
    private LocalDateTime plannedEndTime;

    // 실제 시작일
    @Column(name = "actual_start_time")
    private LocalDateTime actualStartTime;

    // 실제 종료일
    @Column(name = "actual_end_time")
    private LocalDateTime actualEndTime;

    // 현재 상태
    @Column(name = "current_status", length = 20)
    private String currentStatus = "waiting";

    // 우선순위
    @Column(name = "priority")
    private Integer priority;

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
