package com.example.mes_backend.controller;

import com.example.mes_backend.dto.EquipmentDto;
import com.example.mes_backend.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/equipment")
public class EquipmentController {
    private final EquipmentService equipmentService;

    @GetMapping
    public List<EquipmentDto> getEquipments(
            @RequestParam(required = false) String equipmentId,
            @RequestParam(required = false) String equipmentNm,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) String workCenterId
    ) {
        // 2가지 조건 조합
        if (equipmentNm != null && workCenterId != null) {
            return equipmentService.findByEquipmentNmContainingAndWorkCenter_WorkCenterIdContaining(equipmentNm, workCenterId);
        } else if (equipmentNm != null && isActive != null) {
            return equipmentService.findByEquipmentNmContainingAndIsActive(equipmentNm, isActive);
        }
        // 1가지 조건
        else if (equipmentId != null) {
            return equipmentService.findByEquipmentIdContaining(equipmentId);
        } else if (equipmentNm != null) {
            return equipmentService.findByEquipmentNmContaining(equipmentNm);
        } else if (isActive != null) {
            return equipmentService.findByIsActive(isActive);
        } else if (workCenterId != null) {
            return equipmentService.findByWorkCenter_WorkCenterIdContaining(workCenterId);
        }
        // 조건이 없을 경우 (전체 조회)
        else {
            return equipmentService.findAll();
        }
    }
}
