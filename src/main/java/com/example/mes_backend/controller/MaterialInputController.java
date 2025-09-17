package com.example.mes_backend.controller;

import com.example.mes_backend.dto.MaterialInputDto;
import com.example.mes_backend.service.MaterialInputService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/materials-usage")
@RequiredArgsConstructor
public class MaterialInputController {

    private final MaterialInputService materialInputService;

    @GetMapping
    public List<MaterialInputDto> getMaterialInputs(
            @RequestParam(required = false) Integer resultId, // 작업내역 ID
            @RequestParam(required = false) Integer workOrderId, // 작업지시 ID
            @RequestParam(required = false) Integer materialId,  // 자재 ID
            @RequestParam(required = false) String warehouse,    // 창고
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, // 사용일 from
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end    // 사용일 to
    ) {
        boolean hasDateRange = start != null && end != null;

        /** ===================== [네 가지 조건] ===================== */
        if (workOrderId != null && materialId != null && warehouse != null && hasDateRange) {
            return materialInputService.findByWorkOrderIdAndMaterialIdAndWarehouseAndInputDateBetween(workOrderId, materialId, warehouse, start, end);
        }

        /** ===================== [세 가지 조건] ===================== */
        else if (workOrderId != null && materialId != null && warehouse != null) {
            return materialInputService.findByWorkOrderIdAndMaterialIdAndWarehouse(workOrderId, materialId, warehouse);
        } else if (workOrderId != null && materialId != null && hasDateRange) {
            return materialInputService.findByWorkOrderIdAndMaterialIdAndInputDateBetween(workOrderId, materialId, start, end);
        } else if (workOrderId != null && warehouse != null && hasDateRange) {
            return materialInputService.findByWorkOrderIdAndWarehouseAndInputDateBetween(workOrderId, warehouse, start, end);
        } else if (materialId != null && warehouse != null && hasDateRange) {
            return materialInputService.findByMaterialIdAndWarehouseAndInputDateBetween(materialId, warehouse, start, end);
        }

        /** ===================== [두 가지 조건] ===================== */
        else if (workOrderId != null && materialId != null) {
            return materialInputService.findByWorkOrderIdAndMaterialId(workOrderId, materialId);
        } else if (workOrderId != null && warehouse != null) {
            return materialInputService.findByWorkOrderIdAndWarehouse(workOrderId, warehouse);
        } else if (workOrderId != null && hasDateRange) {
            return materialInputService.findByWorkOrderIdAndInputDateBetween(workOrderId, start, end);
        } else if (materialId != null && warehouse != null) {
            return materialInputService.findByMaterialIdAndWarehouse(materialId, warehouse);
        } else if (materialId != null && hasDateRange) {
            return materialInputService.findByMaterialIdAndInputDateBetween(materialId, start, end);
        } else if (warehouse != null && hasDateRange) {
            return materialInputService.findByWarehouseAndInputDateBetween(warehouse, start, end);
        }

        /** ===================== [한 가지 조건] ===================== */
        else if (resultId != null) {
            return materialInputService.findByResultId(resultId);
        } else if (workOrderId != null) {
            return materialInputService.findByWorkOrderId(workOrderId);
        } else if (materialId != null) {
            return materialInputService.findByMaterialId(materialId);
        } else if (warehouse != null) {
            return materialInputService.findByWarehouse(warehouse);
        } else if (hasDateRange) {
            return materialInputService.findByInputDateBetween(start, end);
        }

        /** ===================== [조건 없음 → 전체 조회] ===================== */
        else {
            return materialInputService.findAll();
        }
    }
}
