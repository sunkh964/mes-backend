package com.example.mes_backend.service;

import com.example.mes_backend.dto.EquipmentDto;
import com.example.mes_backend.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;

    // 1. 전체 조회
    public List<EquipmentDto> findAll() {
        return equipmentRepository.findAll().stream()
                .map(EquipmentDto::fromEntity)
                .toList();
    }

    // 2. 설비 ID로 조회 (Containing)
    public List<EquipmentDto> findByEquipmentIdContaining(String equipmentId) {
        return equipmentRepository.findByEquipmentIdContaining(equipmentId).stream()
                .map(EquipmentDto::fromEntity)
                .toList();
    }

    // 3. 설비명으로 조회 (Containing)
    public List<EquipmentDto> findByEquipmentNmContaining(String equipmentNm) {
        return equipmentRepository.findByEquipmentNmContaining(equipmentNm).stream()
                .map(EquipmentDto::fromEntity)
                .toList();
    }

    // 4. 활성 여부로 조회
    public List<EquipmentDto> findByIsActive(boolean isActive) {
        return equipmentRepository.findByIsActive(isActive).stream()
                .map(EquipmentDto::fromEntity)
                .toList();
    }

    // 5. 작업장 ID로 조회 (Containing)
    public List<EquipmentDto> findByWorkCenter_WorkCenterIdContaining(String workCenterId) {
        return equipmentRepository.findByWorkCenter_WorkCenterIdContaining(workCenterId).stream()
                .map(EquipmentDto::fromEntity)
                .toList();
    }

    // 6. 설비명과 활성 여부로 조회
    public List<EquipmentDto> findByEquipmentNmContainingAndIsActive(String equipmentNm, boolean isActive) {
        return equipmentRepository.findByEquipmentNmContainingAndIsActive(equipmentNm, isActive).stream()
                .map(EquipmentDto::fromEntity)
                .toList();
    }

    // 7. 설비명과 작업장 ID로 조회
    public List<EquipmentDto> findByEquipmentNmContainingAndWorkCenter_WorkCenterIdContaining(String equipmentNm, String workCenterId) {
        return equipmentRepository.findByEquipmentNmContainingAndWorkCenter_WorkCenterIdContaining(equipmentNm, workCenterId).stream()
                .map(EquipmentDto::fromEntity)
                .toList();
    }
}
