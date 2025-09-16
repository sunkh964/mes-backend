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
            @RequestParam(required = false) Integer workOrderId,
            @RequestParam(required = false) Integer materialId,
            @RequestParam(required = false) String employeeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        boolean hasDateRange = start != null && end != null;

        // --- 4가지 조건 ---
        if (workOrderId != null && materialId != null && employeeId != null && hasDateRange) {
            return materialInputService.findByWorkOrderIdAndMaterialIdAndEmployeeIdContainingAndInputDateBetween(workOrderId, materialId, employeeId, start, end);
        }
        // --- 3가지 조건 ---
        else if (workOrderId != null && materialId != null && employeeId != null) {
            return materialInputService.findByWorkOrderIdAndMaterialIdAndEmployeeIdContaining(workOrderId, materialId, employeeId);
        } else if (workOrderId != null && materialId != null && hasDateRange) {
            return materialInputService.findByWorkOrderIdAndMaterialIdAndInputDateBetween(workOrderId, materialId, start, end);
        } else if (workOrderId != null && employeeId != null && hasDateRange) {
            return materialInputService.findByWorkOrderIdAndEmployeeIdContainingAndInputDateBetween(workOrderId, employeeId, start, end);
        } else if (materialId != null && employeeId != null && hasDateRange) {
            return materialInputService.findByMaterialIdAndEmployeeIdContainingAndInputDateBetween(materialId, employeeId, start, end);
        }
        // --- 2가지 조건 ---
        else if (workOrderId != null && materialId != null) {
            return materialInputService.findByWorkOrderIdAndMaterialId(workOrderId, materialId);
        } else if (workOrderId != null && employeeId != null) {
            return materialInputService.findByWorkOrderIdAndEmployeeIdContaining(workOrderId, employeeId);
        } else if (workOrderId != null && hasDateRange) {
            return materialInputService.findByWorkOrderIdAndInputDateBetween(workOrderId, start, end);
        } else if (materialId != null && employeeId != null) {
            return materialInputService.findByMaterialIdAndEmployeeIdContaining(materialId, employeeId);
        } else if (materialId != null && hasDateRange) {
            return materialInputService.findByMaterialIdAndInputDateBetween(materialId, start, end);
        } else if (employeeId != null && hasDateRange) {
            return materialInputService.findByEmployeeIdContainingAndInputDateBetween(employeeId, start, end);
        }
        // --- 1가지 조건 ---
        else if (workOrderId != null) {
            return materialInputService.findByWorkOrderId(workOrderId);
        } else if (materialId != null) {
            return materialInputService.findByMaterialId(materialId);
        } else if (employeeId != null) {
            return materialInputService.findByEmployeeIdContaining(employeeId);
        } else if (hasDateRange) {
            return materialInputService.findByInputDateBetween(start, end);
        }
        // --- 조건 없음 ---
        else {
            return materialInputService.findAll();
        }
    }
}
