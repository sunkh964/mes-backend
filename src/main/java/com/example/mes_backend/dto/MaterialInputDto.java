package com.example.mes_backend.dto;

import com.example.mes_backend.entity.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaterialInputDto {
    private Integer inputId;
    private Integer resultId;
    private Integer workOrderId;
    private Integer materialId;
    private Integer quantity;
    private String unit;
    private String warehouse;
    private String location;
    private String employeeId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime inputDate;   // 사용일시
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // DTO를 Entity로 변환
    public MaterialInputEntity toEntity() {
        MaterialInputEntity entity = new MaterialInputEntity();

        // inputId는 자동 생성이므로 DTO에서 받더라도 설정하지 않거나, 수정 시에만 사용
        entity.setInputId(this.inputId);
        entity.setQuantity(this.quantity);
        entity.setUnit(this.unit);
        entity.setWarehouse(this.warehouse);
        entity.setLocation(this.location);
        entity.setInputDate(this.inputDate);
        entity.setRemark(this.remark);

//        // 각 ID를 사용하여 연관 엔티티 객체를 만들어 설정
//        if (this.resultId != null) {
//            WorkResultEntity workResult = new WorkResultEntity();
//            workResult.setResultId(this.resultId);
//            entity.setWorkResult(workResult);
//        }
//        if (this.workOrderId != null) {
//            WorkOrderEntity workOrder = new WorkOrderEntity();
//            workOrder.setWorkOrderId(this.workOrderId);
//            entity.setWorkOrder(workOrder);
//        }
//        if (this.materialId != null) {
//            MaterialEntity material = new MaterialEntity();
//            material.setMaterialId(this.materialId);
//            entity.setMaterial(material);
//        }
//        if (this.employeeId != null) {
//            Employee employee = new Employee();
//            employee.setEmployeeId(this.employeeId);
//            entity.setEmployee(employee);
//        }

        return entity;
    }

    // Entity를 DTO로 변환
    public static MaterialInputDto fromEntity(MaterialInputEntity entity) {
        MaterialInputDto dto = new MaterialInputDto();
        dto.setInputId(entity.getInputId());
        dto.setQuantity(entity.getQuantity());
        dto.setUnit(entity.getUnit());
        dto.setWarehouse(entity.getWarehouse());
        dto.setLocation(entity.getLocation());
        dto.setInputDate(entity.getInputDate());
        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        // 각 연관 객체에서 ID를 추출하여 DTO에 설정 (null 체크 포함)
        if (entity.getWorkResult() != null) {
            dto.setResultId(entity.getWorkResult().getResultId());
        }
        if (entity.getWorkOrder() != null) {
            dto.setWorkOrderId(entity.getWorkOrder().getWorkOrderId());
        }
        if (entity.getMaterial() != null) {
            dto.setMaterialId(entity.getMaterial().getMaterialId());
        }
        if (entity.getEmployee() != null) {
            dto.setEmployeeId(entity.getEmployee().getEmployeeId());
        }

        return dto;
    }
}
