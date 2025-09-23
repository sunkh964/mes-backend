package com.example.mes_backend.dto;

import com.example.mes_backend.entity.WorkOrderEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkOrderDto {

    private Integer workOrderId;

    private String processId;
    private Integer blockPlanId;
    private Integer blockId;
    private String workCenterId;
    private String equipmentId;
    private String employeeId;

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
        workOrder.setQuantityProduced(this.quantityProduced);
        workOrder.setPlannedStartTime(this.plannedStartTime);
        workOrder.setPlannedEndTime(this.plannedEndTime);
        workOrder.setActualStartTime(this.actualStartTime);
        workOrder.setActualEndTime(this.actualEndTime);
        workOrder.setCurrentStatus(this.currentStatus);
        workOrder.setPriority(this.priority);
        workOrder.setRemark(this.remark);
        workOrder.setCreatedAt(this.createdAt);
        workOrder.setUpdatedAt(this.updatedAt);

        // 연관 엔티티는 ID만으로 set 불가 → 서비스 레이어에서 주입 필요
        // 예: processRepository.findById(dto.getProcessId()) 등
        return workOrder;
    }

    // Entity → DTO 변환
    public static WorkOrderDto fromEntity(WorkOrderEntity entity) {
        WorkOrderDto dto = new WorkOrderDto();
        dto.setWorkOrderId(entity.getWorkOrderId());
        dto.setProcessId(entity.getProcess() != null ? entity.getProcess().getProcessId() : null);
        dto.setBlockPlanId(entity.getBlockPlan() != null ? entity.getBlockPlan().getBlockPlanId() : null);
        dto.setBlockId(entity.getBlock() != null ? entity.getBlock().getBlockId() : null);
        dto.setWorkCenterId(entity.getWorkCenter() != null ? entity.getWorkCenter().getWorkCenterId() : null);
        dto.setEquipmentId(entity.getEquipment() != null ? entity.getEquipment().getEquipmentId() : null);
        dto.setEmployeeId(entity.getEmployee() != null ? entity.getEmployee().getEmployeeId() : null);
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
