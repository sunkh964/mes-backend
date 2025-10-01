package com.example.mes_backend.dto;

import com.example.mes_backend.entity.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkResultDto {

    private Integer resultId;
    private Integer workOrderId;
    private String employeeId;
    private Integer completedQuantity;
    private Integer defectiveQuantity;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime endTime;

    private String workCenterId;
    private String equipmentId;
    private String status;
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    // DTO를 Entity로 변환 (데이터 저장/수정 시)
    public WorkResultEntity toEntity() {
        WorkResultEntity entity = new WorkResultEntity();

        // resultId는 자동 생성이므로 여기서 설정하지 않음
        entity.setCompletedQuantity(this.completedQuantity);
        entity.setDefectiveQuantity(this.defectiveQuantity);
        entity.setStartTime(this.startTime);
        entity.setEndTime(this.endTime);
        entity.setStatus(this.status);
        entity.setRemark(this.remark);

        // 각 ID를 사용하여 연관 엔티티 객체를 만들어 설정
        if (this.workOrderId != null) {
            WorkOrderEntity workOrder = new WorkOrderEntity();
            workOrder.setWorkOrderId(this.workOrderId);
            entity.setWorkOrder(workOrder);
        }
        if (this.employeeId != null) {
            Employee employee = new Employee();
            employee.setEmployeeId(this.employeeId);
            entity.setEmployee(employee);
        }
        if (this.workCenterId != null) {
            WorkCenterEntity workCenter = new WorkCenterEntity();
            workCenter.setWorkCenterId(this.workCenterId);
            entity.setWorkCenter(workCenter);
        }
        if (this.equipmentId != null) {
            EquipmentEntity equipment = new EquipmentEntity();
            equipment.setEquipmentId(this.equipmentId);
            entity.setEquipment(equipment);
        }

        return entity;
    }

    // Entity를 DTO로 변환 (데이터 조회 시)
    public static WorkResultDto fromEntity(WorkResultEntity entity) {
        WorkResultDto dto = new WorkResultDto();
        dto.setResultId(entity.getResultId());
        dto.setCompletedQuantity(entity.getCompletedQuantity());
        dto.setDefectiveQuantity(entity.getDefectiveQuantity());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setStatus(entity.getStatus());
        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        // 각 연관 객체에서 ID를 추출하여 DTO에 설정 (null 체크 포함)
        if (entity.getWorkOrder() != null) {
            dto.setWorkOrderId(entity.getWorkOrder().getWorkOrderId());
        }
        if (entity.getEmployee() != null) {
            dto.setEmployeeId(entity.getEmployee().getEmployeeId());
        }
        if (entity.getWorkCenter() != null) {
            dto.setWorkCenterId(entity.getWorkCenter().getWorkCenterId());
        }
        if (entity.getEquipment() != null) {
            dto.setEquipmentId(entity.getEquipment().getEquipmentId());
        }

        return dto;
    }
}
