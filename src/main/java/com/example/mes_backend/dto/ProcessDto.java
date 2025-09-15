package com.example.mes_backend.dto;

import com.example.mes_backend.entity.ProcessEntity;
import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
public class ProcessDto {
    private String processId;
    private String processNm;
    private String processInfo;
    private Integer processSequence;
    private Integer standardTime;
    private Boolean isActive;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // DTO를 Entity로 변환하는 메소드 (주로 데이터 저장/수정 시 사용)
    public ProcessEntity toEntity(){
        ProcessEntity processEntity = new ProcessEntity();
        processEntity.setProcessId(this.processId);
        processEntity.setProcessNm(this.processNm);
        processEntity.setProcessInfo(this.processInfo);
        processEntity.setProcessSequence(this.processSequence);
        processEntity.setStandardTime(this.standardTime);
        processEntity.setIsActive(this.isActive);
        processEntity.setRemark(this.remark);
        // createdAt, updatedAt은 DB에서 자동 생성되므로 여기서 설정x
        return processEntity;
    }

    // Entity를 DTO로 변환하는 정적(static) 메소드 (주로 데이터 조회/삭제 시 사용)
    public static ProcessDto fromEntity(ProcessEntity entity) {
        ProcessDto dto = new ProcessDto();
        dto.setProcessId(entity.getProcessId());
        dto.setProcessNm(entity.getProcessNm());
        dto.setProcessInfo(entity.getProcessInfo());
        dto.setProcessSequence(entity.getProcessSequence());
        dto.setStandardTime(entity.getStandardTime());
        dto.setIsActive(entity.getIsActive());
        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}