package com.example.mes_backend.dto;

import com.example.mes_backend.entity.BlockQCEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlockQCDto {

    private Integer blockQCId;

    private Integer workOrderId;
    private Integer blockId;
    private String employeeId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime inspectionDate;

    private String result; // PASS, FAIL, PARTIAL, PENDING

    private Integer passQuantity;
    private Integer failQuantity;

    private String defectType;
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime updatedAt;

    // DTO → Entity 변환
    public BlockQCEntity toEntity() {
        BlockQCEntity blockQC = new BlockQCEntity();
        blockQC.setBlockQCId(this.blockQCId);
        blockQC.setInspectionDate(this.inspectionDate);
        blockQC.setResult(this.result);
        blockQC.setPassQuantity(this.passQuantity);
        blockQC.setFailQuantity(this.failQuantity);
        blockQC.setDefectType(this.defectType);
        blockQC.setRemark(this.remark);
        blockQC.setCreatedAt(this.createdAt);
        blockQC.setUpdatedAt(this.updatedAt);

        // 연관 엔티티 (workOrder, block, employee)는 서비스 레이어에서 Repository로 조회 후 주입해야 함
        // 예: workOrderRepository.findById(dto.getWorkOrderId()) 등
        return blockQC;
    }

    // Entity → DTO 변환
    public static BlockQCDto fromEntity(BlockQCEntity entity) {
        BlockQCDto dto = new BlockQCDto();
        dto.setBlockQCId(entity.getBlockQCId());

        // 연관 엔티티 안전 매핑 (null 체크)
        dto.setWorkOrderId(entity.getWorkOrder() != null ? entity.getWorkOrder().getWorkOrderId() : null);
        dto.setBlockId(entity.getBlock() != null ? entity.getBlock().getBlockId() : null);
        dto.setEmployeeId(entity.getEmployee() != null ? entity.getEmployee().getEmployeeId() : null);

        // 기본 속성 매핑
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
