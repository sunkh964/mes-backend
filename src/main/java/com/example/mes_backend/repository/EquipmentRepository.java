package com.example.mes_backend.repository;

import com.example.mes_backend.entity.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<EquipmentEntity, String> {
    // 설비 ID를 포함하는 데이터 검색
    List<EquipmentEntity> findByEquipmentIdContaining(String equipmentId);

    // 설비명을 포함하는 데이터 검색
    List<EquipmentEntity> findByEquipmentNmContaining(String equipmentNm);

    // 활성 여부가 일치하는 데이터 검색
    List<EquipmentEntity> findByIsActive(boolean isActive);

    // 작업장 ID가 일치하는 데이터 검색
    List<EquipmentEntity> findByWorkCenter_WorkCenterIdContaining(String workCenterId);

    // 설비명을 포함하고 활성 여부가 일치하는 데이터 검색
    List<EquipmentEntity> findByEquipmentNmContainingAndIsActive(String equipmentNm, boolean isActive);

    // 설비명을 포함하고 작업장 ID가 일치하는 데이터 검색
    List<EquipmentEntity> findByEquipmentNmContainingAndWorkCenter_WorkCenterIdContaining(String equipmentNm, String workCenterId);

}