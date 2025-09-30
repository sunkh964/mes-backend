package com.example.mes_backend.dto;

import com.example.mes_backend.entity.BlockEntity;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BlockDto {
    private Integer blockId;
    private String vesselId;
    private String blockNm;
    private String blockType;
    private String dimensions;
    private Integer weight;
    private String currentStatus;
    private LocalDate plannedStartDate;
    private LocalDate plannedEndDate;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /** DTO → Entity 변환 */
    public BlockEntity toEntity() {
        BlockEntity entity = new BlockEntity();
        entity.setBlockId(this.blockId);
        entity.setVesselId(this.vesselId);
        entity.setBlockNm(this.blockNm);
        entity.setBlockType(this.blockType);
        entity.setDimensions(this.dimensions);
        entity.setWeight(this.weight);
        entity.setCurrentStatus(this.currentStatus != null ? this.currentStatus : "planned");
        entity.setPlannedStartDate(this.plannedStartDate);
        entity.setPlannedEndDate(this.plannedEndDate);
        entity.setActualStartDate(this.actualStartDate);
        entity.setActualEndDate(this.actualEndDate);
        entity.setRemark(this.remark);
        // createdAt, updatedAt은 DB에서 자동 관리 → 세팅하지 않음
        return entity;
    }

    /** Entity → DTO 변환 */
    public static BlockDto fromEntity(BlockEntity entity) {
        BlockDto dto = new BlockDto();
        dto.setBlockId(entity.getBlockId());
        dto.setVesselId(entity.getVesselId());
        dto.setBlockNm(entity.getBlockNm());
        dto.setBlockType(entity.getBlockType());
        dto.setDimensions(entity.getDimensions());
        dto.setWeight(entity.getWeight());
        dto.setCurrentStatus(entity.getCurrentStatus());
        dto.setPlannedStartDate(entity.getPlannedStartDate());
        dto.setPlannedEndDate(entity.getPlannedEndDate());
        dto.setActualStartDate(entity.getActualStartDate());
        dto.setActualEndDate(entity.getActualEndDate());
        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
