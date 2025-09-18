package com.example.mes_backend.dto;

import com.example.mes_backend.entity.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class QualityControlDto {
    private Integer qcId;           // 품질검사 ID
    private String purchaseOrderId; // 발주번호
    private Integer purchaseOrderDetailId ; // 발주상세
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
        entity.setInspectionDate(this.inspectionDate);
        entity.setResult(this.result);
        entity.setPassQuantity(this.passQuantity);
        entity.setFailQuantity(this.failQuantity);
        entity.setDefectType(this.defectType);
        entity.setRemark(this.remark);


        // 각 ID를 사용하여 연관 엔티티 객체를 만들어 설정
        if (this.purchaseOrderId != null) {
            PurchaseOrderEntity po = new PurchaseOrderEntity();
            po.setPurchaseOrderId(this.purchaseOrderId);
            entity.setPurchaseOrderId(po);
        }
        if (this.purchaseOrderDetailId != null) {
            PurchaseDetailEntity pod = new PurchaseDetailEntity();
            pod.setOrderDetailId(this.purchaseOrderDetailId);
            entity.setPurchaseOrderDetailId(pod);
        }
        if (this.workOrderId != null) {
            WorkOrderEntity wo = new WorkOrderEntity();
            wo.setWorkOrderId(this.workOrderId);
            entity.setWorkOrderId(wo);
        }
        if (this.materialId != null) {
            MaterialEntity material = new MaterialEntity();
            material.setMaterialId(this.materialId);
            entity.setMaterialId(material);
        }
        if (this.inspectorId != null) {
            Employee inspector = new Employee();
            inspector.setEmployeeId(this.inspectorId);
            entity.setInspectorId(inspector);
        }

        return entity;
    }

    // Entity -> DTO
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

        // 연관 엔티티에서 ID를 추출하여 DTO에 설정합니다.
        dto.setPurchaseOrderId(entity.getPurchaseOrderId().getPurchaseOrderId());
        dto.setPurchaseOrderDetailId(entity.getPurchaseOrderDetailId().getOrderDetailId());

        // workOrder는 NULL일 수 있으므로 null 체크를 추가합니다.
        if (entity.getWorkOrderId() != null) {
            dto.setWorkOrderId(entity.getWorkOrderId().getWorkOrderId());
        }
        dto.setMaterialId(entity.getMaterialId().getMaterialId());
        dto.setInspectorId(entity.getInspectorId().getEmployeeId());

        return dto;
    }
}
