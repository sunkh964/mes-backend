package com.example.mes_backend.dto;

import com.example.mes_backend.entity.QualityControlEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class QualityControlDto {
    private Integer qcId;           // 품질검사 ID
    private String purchaseOrderId; // 발주번호
    private Integer orderDetailId ; // 발주상세
    private Integer workOrderId;    // 작업지시 ID
    private Integer materialId;     // 자재 ID
    private String inspectorId;     // 검사자 ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime inspectionDate; // 검사일
    private String result;          // 검사결과 (합격/불합격/재검사)
    private Integer passQuantity;   // 합격 수량
    private Integer failQuantity;   // 불합격 수량
    private String defectType;      // 불량 유형
    private String remark;           // 비고
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt; // 생성일
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt; // 수정일

    // DTO -> Entity
    public QualityControlEntity toEntity() {
        QualityControlEntity entity = new QualityControlEntity();
        entity.setQcId(this.qcId);
        entity.setPurchaseOrderId(this.purchaseOrderId);
        entity.setOrderDetailId(this.orderDetailId);
        entity.setWorkOrderId(this.workOrderId);
        entity.setMaterialId(this.materialId);
        entity.setInspectorId(this.inspectorId);
        entity.setInspectionDate(this.inspectionDate);
        entity.setResult(this.result);
        entity.setPassQuantity(this.passQuantity);
        entity.setFailQuantity(this.failQuantity);
        entity.setDefectType(this.defectType);
        entity.setRemark(this.remark);
        entity.setCreatedAt(this.createdAt);
        entity.setUpdatedAt(this.updatedAt);
        return entity;
    }

    // Entity -> DTO
    public static QualityControlDto fromEntity(QualityControlEntity entity) {
        QualityControlDto dto = new QualityControlDto();
        dto.setQcId(entity.getQcId());
        dto.setPurchaseOrderId(entity.getPurchaseOrderId());
        dto.setOrderDetailId(entity.getOrderDetailId());
        dto.setWorkOrderId(entity.getWorkOrderId());
        dto.setMaterialId(entity.getMaterialId());
        dto.setInspectorId(entity.getInspectorId());
        dto.setInspectionDate(entity.getInspectionDate());
        dto.setResult(entity.getResult());
        dto.setPassQuantity(entity.getPassQuantity());
        dto.setFailQuantity(entity.getFailQuantity());
        dto.setDefectType(entity.getDefectType());
        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
