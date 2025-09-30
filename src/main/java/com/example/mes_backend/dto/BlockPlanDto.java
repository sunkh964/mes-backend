package com.example.mes_backend.dto;

import com.example.mes_backend.entity.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BlockPlanDto {
    private Integer blockPlanId;     // 블록 계획 ID
    private String planId;
//    private String vesselId;         // 선박 ID (vessel 엔티티에서 참조)
    private String processId;        // 공정 ID (process 엔티티에서 참조)
    private String processNm;   // 공정 이름 (조회 전용)

    private Integer blockId;         // 블록 ID (block 엔티티에서 참조)
    private String blockNm;     // 블록 이름 (조회 전용)


    private Integer planQty;         // 계획 수량

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;     // 시작일

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;       // 종료일

    private Integer status;          // 상태값 (ex. 계획/진행/완료 등)
    private String remark;           // 비고

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // DTO -> Entity
    public BlockPlanEntity toEntity(){
        BlockPlanEntity blockPlanEntity = new BlockPlanEntity();

        // ===== 연관관계 매핑 =====
//        if (this.vesselId != null) {
//            VesselEntity vessel = new VesselEntity();
//            vessel.setVesselId(this.vesselId);  // PK만 세팅
//            blockPlanEntity.setVesselEntity(vessel);
//        }

        if (this.planId != null) {
            ProjectPlanEntity projectPlan = new ProjectPlanEntity();
            projectPlan.setPlanId(this.planId);  // PK만 세팅
            blockPlanEntity.setProjectPlanEntity(projectPlan);
        }

        if (this.processId != null) {
            ProcessEntity process = new ProcessEntity();
            process.setProcessId(this.processId);
            blockPlanEntity.setProcess(process);
        }

        if (this.blockId != null) {
            BlockEntity block = new BlockEntity();
            block.setBlockId(this.blockId);
            blockPlanEntity.setBlockEntity(block);
        }

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

    // Entity → DTO 변환
    public static BlockPlanDto fromEntity(BlockPlanEntity entity) {
        BlockPlanDto dto = new BlockPlanDto();
        dto.setBlockPlanId(entity.getBlockPlanId());
        // Null-safe 처리
//        if (entity.getVesselEntity() != null) {
//            dto.setVesselId(entity.getVesselEntity().getVesselId());
//        }

        if (entity.getProjectPlanEntity() != null) {
            dto.setPlanId(entity.getProjectPlanEntity().getPlanId());
        }
        if (entity.getProcess() != null) {
            dto.setProcessId(entity.getProcess().getProcessId());
            dto.setProcessNm(entity.getProcess().getProcessNm());
        }
        if (entity.getBlockEntity() != null) {
            dto.setBlockId(entity.getBlockEntity().getBlockId());
            dto.setBlockNm(entity.getBlockEntity().getBlockNm());
        }
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
