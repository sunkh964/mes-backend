package com.example.mes_backend.dto;

import com.example.mes_backend.entity.ShipmentsEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 출하 DTO
 * - Entity ↔ DTO 변환 메서드 포함
 * - 연관 엔티티(@ManyToOne)는 ID 값으로만 표현
 */
@Data
public class ShipmentsDto {

    // 출하번호 (PK)
    private String shipmentId;

    // 외래키 → 연관 엔티티 ID만 표현
    private String salesOrderId;   // 수주 ID
    private String customerId;     // 고객 ID
    private String vesselId;       // 선박 ID

    // 일정
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime plannedShipDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime actualShipDate;

    // 상태 (0: 계획, 1: 출하, 2: 인도완료, 3: 취소)
    private Integer status;

    // 등록/승인 정보
    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approvedDate;

    private String approvedBy;

    // 비고
    private String remark;

    // 생성/수정일
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    /**
     * DTO → Entity 변환
     * (연관 엔티티는 서비스 계층에서 repository로 주입 필요)
     */
    public ShipmentsEntity toEntity() {
        ShipmentsEntity entity = new ShipmentsEntity();
        entity.setShipmentId(this.shipmentId);
        entity.setPlannedShipDate(this.plannedShipDate);
        entity.setActualShipDate(this.actualShipDate);
        entity.setStatus(this.status);
        entity.setCreatedBy(this.createdBy);
        entity.setApprovedDate(this.approvedDate);
        entity.setApprovedBy(this.approvedBy);
        entity.setRemark(this.remark);
        entity.setCreatedAt(this.createdAt);
        entity.setUpdatedAt(this.updatedAt);

        // salesOrder, customer, vessel은 ID만으로 직접 set 불가 → 서비스에서 주입
        return entity;
    }

    /**
     * Entity → DTO 변환
     */
    public static ShipmentsDto fromEntity(ShipmentsEntity entity) {
        ShipmentsDto dto = new ShipmentsDto();
        dto.setShipmentId(entity.getShipmentId());
        dto.setSalesOrderId(entity.getSalesOrder().getSalesOrderId());
        dto.setCustomerId(entity.getCustomer().getCustomerId());
        dto.setVesselId(entity.getVessel().getVesselId());
        dto.setPlannedShipDate(entity.getPlannedShipDate());
        dto.setActualShipDate(entity.getActualShipDate());
        dto.setStatus(entity.getStatus());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setApprovedDate(entity.getApprovedDate());
        dto.setApprovedBy(entity.getApprovedBy());
        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}