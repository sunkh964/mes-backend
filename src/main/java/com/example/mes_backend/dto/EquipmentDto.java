package com.example.mes_backend.dto;

import com.example.mes_backend.entity.EquipmentEntity;
import com.example.mes_backend.entity.WorkCenterEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EquipmentDto {
    private String equipmentId;
    private String equipmentNm;
    private String equipmentType;
    private String workCenterId; // Entity의 WorkCenterEntity 객체를 String ID로 표현
    private Integer capacity;
    private String capacityUnit;   // EA, m, m², TON 등
    private Boolean isActive;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // DTO를 Entity로 변환 (데이터 저장/수정 시)
    public EquipmentEntity toEntity() {
        EquipmentEntity entity = new EquipmentEntity();
        entity.setEquipmentId(this.equipmentId);
        entity.setEquipmentNm(this.equipmentNm);
        entity.setEquipmentType(this.equipmentType);
        entity.setCapacity(this.capacity);
        entity.setCapacityUnit(this.capacityUnit);
        entity.setIsActive(this.isActive);
        entity.setRemark(this.remark);

        // workCenterId 문자열로 WorkCenterEntity 객체를 만들어 설정
        if (this.workCenterId != null) {
            WorkCenterEntity workCenter = new WorkCenterEntity();
            workCenter.setWorkCenterId(this.workCenterId);
            entity.setWorkCenter(workCenter);
        }

        return entity;
    }

    // Entity를 DTO로 변환 (데이터 조회 시)
    public static EquipmentDto fromEntity(EquipmentEntity entity) {
        EquipmentDto dto = new EquipmentDto();
        dto.setEquipmentId(entity.getEquipmentId());
        dto.setEquipmentNm(entity.getEquipmentNm());
        dto.setEquipmentType(entity.getEquipmentType());
        dto.setCapacity(entity.getCapacity());
        dto.setCapacityUnit(entity.getCapacityUnit());
        dto.setIsActive(entity.getIsActive());
        dto.setRemark(entity.getRemark());

        // 연관된 WorkCenterEntity 객체에서 ID를 꺼내 DTO에 설정
        if (entity.getWorkCenter() != null) {
            dto.setWorkCenterId(entity.getWorkCenter().getWorkCenterId());
        }

        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

}
