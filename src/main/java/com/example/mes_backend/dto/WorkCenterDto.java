package com.example.mes_backend.dto;

import com.example.mes_backend.entity.ProcessEntity;
import com.example.mes_backend.entity.WorkCenterEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkCenterDto {
    private String workCenterId;
    private String workCenterNm;
    private String location;
    private Boolean isActive;
    private String processId;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // DTO를 Entity로 변환 (데이터 저장/수정 시 사용)
    public WorkCenterEntity toEntity() {
        WorkCenterEntity entity = new WorkCenterEntity();
        entity.setWorkCenterId(this.workCenterId);
        entity.setWorkCenterNm(this.workCenterNm);
        entity.setLocation(this.location);
        entity.setIsActive(this.isActive);
        entity.setRemark(this.remark);

        // processId 문자열로 ProcessEntity 객체를 만들어 설정합니다.
        if (this.processId != null) {
            ProcessEntity processEntity = new ProcessEntity();
            processEntity.setProcessId(this.processId);
            entity.setProcess(processEntity);
        }

        return entity;
    }


    // Entity를 DTO로 변환 (데이터 조회 시 사용)
    public static WorkCenterDto fromEntity(WorkCenterEntity entity) {
        WorkCenterDto dto = new WorkCenterDto();
        dto.setWorkCenterId(entity.getWorkCenterId());
        dto.setWorkCenterNm(entity.getWorkCenterNm());
        dto.setLocation(entity.getLocation());
        dto.setIsActive(entity.getIsActive());
        dto.setRemark(entity.getRemark());

        // 연관된 ProcessEntity가 null이 아닐 경우에만 processId를 가져옵니다.
        if (entity.getProcess() != null) {
            dto.setProcessId(entity.getProcess().getProcessId());
        }
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
