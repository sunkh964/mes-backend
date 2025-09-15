package com.example.mes_backend.dto;

import com.example.mes_backend.entity.BlockPlanEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BlockPlanDto {
    private Integer blockPlanId;     // 블록 계획 ID
    private String vesselId;         // 선박 ID (vessel 엔티티에서 참조)
    private String processId;        // 공정 ID (process 엔티티에서 참조)
    private Integer blockId;         // 블록 ID (block 엔티티에서 참조)
    private Integer planQty;         // 계획 수량

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;     // 시작일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;       // 종료일

    private Integer status;          // 상태값 (ex. 계획/진행/완료 등)
    private String remark;           // 비고

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    // DTO -> Entity
    public BlockPlanEntity toEntity(){
        BlockPlanEntity blockPlanEntity = new BlockPlanEntity();

        blockPlanEntity.setBlockPlanId((this.blockPlanId));
        blockPlanEntity.setPlanQty(this.planQty);
        blockPlanEntity.setStartDate(this.startDate);
        blockPlanEntity.setEndDate(this.endDate);
        blockPlanEntity.setStatus(this.status);
        blockPlanEntity.setRemark(this.remark);
        blockPlanEntity.setCreatedAt(this.createdAt);
        blockPlanEntity.setUpdatedAt(this.updatedAt);
        return blockPlanEntity;

    }

    public static BlockPlanDto fromEntity(BlockPlanEntity entity) {
        BlockPlanDto dto = new BlockPlanDto();
        dto.setBlockPlanId(entity.getBlockPlanId());
        dto.setVesselId(entity.getVesselEntity().getVesselId());
        dto.setProcessId(entity.getProcess().getProcessId());
        dto.setBlockId(entity.getBlockEntity().getBlockId());
        dto.setPlanQty(entity.getPlanQty());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setStatus(entity.getStatus());
        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }


}
