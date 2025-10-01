package com.example.mes_backend.dto;

import com.example.mes_backend.entity.WorkOrderEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkOrderDto {

    private Integer workOrderId;

    private String processId;
    private String processNm;      // 공정명 (조회 전용)

    private Integer blockPlanId;
    private Integer blockId;
    private String blockNm;        // 블록명 (조회 전용)

    private String workCenterId;
    private String workCenterNm;   // 작업장명 (조회 전용)

    private String equipmentId;
    private String equipmentNm;    // 설비명 (조회 전용)

    private Integer processStandardTime;

    private String employeeId;
    private String employeeNm;     // 직원명 (조회 전용)

    private String instruction;

    private Integer quantityToProduce;
    private Integer quantityProduced;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime plannedStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime plannedEndTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime actualStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime actualEndTime;

    private String currentStatus;
    private Integer priority;
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // DTO → Entity 변환
    public WorkOrderEntity toEntity() {
        WorkOrderEntity workOrder = new WorkOrderEntity();
        workOrder.setWorkOrderId(this.workOrderId);
        workOrder.setInstruction(this.instruction);
        workOrder.setQuantityToProduce(this.quantityToProduce);
        workOrder.setQuantityProduced(this.quantityProduced != null ? this.quantityProduced : 0);
        workOrder.setPlannedStartTime(this.plannedStartTime);
        workOrder.setPlannedEndTime(this.plannedEndTime);
        workOrder.setActualStartTime(this.actualStartTime);
        workOrder.setActualEndTime(this.actualEndTime);
        workOrder.setCurrentStatus(this.currentStatus != null ? this.currentStatus : "waiting");
        workOrder.setPriority(this.priority);
        workOrder.setRemark(this.remark);

        // 연관 엔티티는 Service에서 세팅 or 껍데기 엔티티 생성 (선택)
        return workOrder;
    }

    // Entity → DTO 변환
    public static WorkOrderDto fromEntity(WorkOrderEntity entity) {
        WorkOrderDto dto = new WorkOrderDto();
        dto.setWorkOrderId(entity.getWorkOrderId());

        if (entity.getProcess() != null) {
            dto.setProcessId(entity.getProcess().getProcessId());
            dto.setProcessNm(entity.getProcess().getProcessNm());
        }

        if (entity.getBlockPlan() != null) {
            dto.setBlockPlanId(entity.getBlockPlan().getBlockPlanId());
        }

        if (entity.getBlock() != null) {
            dto.setBlockId(entity.getBlock().getBlockId());
            dto.setBlockNm(entity.getBlock().getBlockNm());
        }

        if (entity.getWorkCenter() != null) {
            dto.setWorkCenterId(entity.getWorkCenter().getWorkCenterId());
            dto.setWorkCenterNm(entity.getWorkCenter().getWorkCenterNm());
        }

        if (entity.getEquipment() != null) {
            dto.setEquipmentId(entity.getEquipment().getEquipmentId());
            dto.setEquipmentNm(entity.getEquipment().getEquipmentNm());
        }

        if (entity.getEmployee() != null) {
            dto.setEmployeeId(entity.getEmployee().getEmployeeId());
            dto.setEmployeeNm(entity.getEmployee().getEmployeeNm());
        }

        // 공정 표준시간
        if (entity.getProcess() != null) {
            dto.setProcessStandardTime(entity.getProcess().getStandardTime());
        }

        dto.setInstruction(entity.getInstruction());
        dto.setQuantityToProduce(entity.getQuantityToProduce());
        dto.setQuantityProduced(entity.getQuantityProduced());
        dto.setPlannedStartTime(entity.getPlannedStartTime());
        dto.setPlannedEndTime(entity.getPlannedEndTime());
        dto.setActualStartTime(entity.getActualStartTime());
        dto.setActualEndTime(entity.getActualEndTime());
        dto.setCurrentStatus(entity.getCurrentStatus());
        dto.setPriority(entity.getPriority());
        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }
}
