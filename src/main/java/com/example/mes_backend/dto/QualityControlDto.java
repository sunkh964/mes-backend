package com.example.mes_backend.dto;

import com.example.mes_backend.entity.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QualityControlDto {
    private Integer qcId;
    private String purchaseOrderId;
    private Integer orderDetailId;
    private Integer workOrderId;
    private Integer materialId;
    private String inspectorId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime inspectionDate;

    private String result;
    private Integer passQuantity;
    private Integer failQuantity;
    private String defectType;
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    // === DTO → Entity ===
    public QualityControlEntity toEntity() {
        QualityControlEntity entity = new QualityControlEntity();
        entity.setQcId(qcId);
        entity.setInspectionDate(inspectionDate);
        entity.setResult(result != null ? result : "PENDING"); // 기본값 처리
        entity.setPassQuantity(passQuantity);
        entity.setFailQuantity(failQuantity);
        entity.setDefectType(defectType);
        entity.setRemark(remark);

        if (purchaseOrderId != null) {
            PurchaseOrderEntity po = new PurchaseOrderEntity();
            po.setPurchaseOrderId(purchaseOrderId);
            entity.setPurchaseOrderId(po);
        }
        if (orderDetailId != null) {
            PurchaseDetailEntity pod = new PurchaseDetailEntity();
            pod.setOrderDetailId(orderDetailId);
            entity.setOrderDetailId(pod);
        }
        if (workOrderId != null) {
            WorkOrderEntity wo = new WorkOrderEntity();
            wo.setWorkOrderId(workOrderId);
            entity.setWorkOrderId(wo);
        }
        if (materialId != null) {
            MaterialEntity material = new MaterialEntity();
            material.setMaterialId(materialId);
            entity.setMaterialId(material);
        }
        if (inspectorId != null) {
            Employee inspector = new Employee();
            inspector.setEmployeeId(inspectorId);
            entity.setInspectorId(inspector);
        }
        return entity;
    }

    // === Entity → DTO ===
    public static QualityControlDto fromEntity(QualityControlEntity entity) {
        QualityControlDto dto = new QualityControlDto();
        dto.setQcId(entity.getQcId());
        dto.setResult(entity.getResult());
        dto.setPassQuantity(entity.getPassQuantity());
        dto.setFailQuantity(entity.getFailQuantity());
        dto.setDefectType(entity.getDefectType());
        dto.setRemark(entity.getRemark());
        dto.setInspectionDate(entity.getInspectionDate());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        if (entity.getPurchaseOrderId() != null) {
            dto.setPurchaseOrderId(entity.getPurchaseOrderId().getPurchaseOrderId());
        }
        if (entity.getOrderDetailId() != null) {
            dto.setOrderDetailId(entity.getOrderDetailId().getOrderDetailId());
        }
        if (entity.getWorkOrderId() != null) {
            dto.setWorkOrderId(entity.getWorkOrderId().getWorkOrderId());
        }
        if (entity.getMaterialId() != null) {
            dto.setMaterialId(entity.getMaterialId().getMaterialId());
        }
        if (entity.getInspectorId() != null) {
            dto.setInspectorId(entity.getInspectorId().getEmployeeId());
        }
        return dto;
    }
}
