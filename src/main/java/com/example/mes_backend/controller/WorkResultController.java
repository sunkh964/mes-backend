package com.example.mes_backend.controller;

import com.example.mes_backend.dto.WorkResultDto;
import com.example.mes_backend.service.WorkResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/work-results")
public class WorkResultController {
    private final WorkResultService workResultService;

    @GetMapping
    public List<WorkResultDto> getWorkResults(
            @RequestParam(required = false) Integer workOrderId,
            @RequestParam(required = false) String employeeId,
            @RequestParam(required = false) String workCenterId,
            @RequestParam(required = false) String equipmentId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        // --- 5가지 조건 ---
        if (workOrderId != null && employeeId != null && workCenterId != null && equipmentId != null && status != null) {
            return workResultService.findByWorkOrderIdAndEmployeeIdAndWorkCenterIdAndEquipmentIdAndStatus(workOrderId, employeeId, workCenterId, equipmentId, status);
        }
        // --- 4가지 조건 ---
        else if (workOrderId != null && employeeId != null && status != null && start != null && end != null) {
            return workResultService.findByWorkOrderIdAndEmployeeIdAndStatusAndStartTimeBetween(workOrderId, employeeId, status, start, end);
        } else if (workOrderId != null && workCenterId != null && equipmentId != null && status != null) {
            return workResultService.findByWorkOrderIdAndWorkCenterIdAndEquipmentIdAndStatus(workOrderId, workCenterId, equipmentId, status);
        }
        // --- 3가지 조건 ---
        else if (employeeId != null && status != null && start != null && end != null) {
            return workResultService.findByEmployeeIdAndStatusAndStartTimeBetween(employeeId, status, start, end);
        } else if (workCenterId != null && equipmentId != null && status != null) {
            return workResultService.findByWorkCenterIdAndEquipmentIdAndStatus(workCenterId, equipmentId, status);
        } else if (workOrderId != null && workCenterId != null && status != null) {
            return workResultService.findByWorkOrderIdAndWorkCenterIdAndStatus(workOrderId, workCenterId, status);
        } else if (workOrderId != null && employeeId != null && status != null) {
            return workResultService.findByWorkOrderIdAndEmployeeIdAndStatus(workOrderId, employeeId, status);
        }
        // --- 2가지 조건 ---
        else if (status != null && start != null && end != null) {
            return workResultService.findByStatusAndStartTimeBetween(status, start, end);
        } else if (equipmentId != null && start != null && end != null) {
            return workResultService.findByEquipmentIdAndStartTimeBetween(equipmentId, start, end);
        } else if (equipmentId != null && status != null) {
            return workResultService.findByEquipmentIdAndStatus(equipmentId, status);
        } else if (workCenterId != null && start != null && end != null) {
            return workResultService.findByWorkCenterIdAndStartTimeBetween(workCenterId, start, end);
        } else if (workCenterId != null && status != null) {
            return workResultService.findByWorkCenterIdAndStatus(workCenterId, status);
        } else if (workCenterId != null && equipmentId != null) {
            return workResultService.findByWorkCenterIdAndEquipmentId(workCenterId, equipmentId);
        } else if (employeeId != null && start != null && end != null) {
            return workResultService.findByEmployeeIdAndStartTimeBetween(employeeId, start, end);
        } else if (employeeId != null && status != null) {
            return workResultService.findByEmployeeIdAndStatus(employeeId, status);
        } else if (employeeId != null && equipmentId != null) {
            return workResultService.findByEmployeeIdAndEquipmentId(employeeId, equipmentId);
        } else if (employeeId != null && workCenterId != null) {
            return workResultService.findByEmployeeIdAndWorkCenterId(employeeId, workCenterId);
        } else if (workOrderId != null && start != null && end != null) {
            return workResultService.findByWorkOrderIdAndStartTimeBetween(workOrderId, start, end);
        } else if (workOrderId != null && status != null) {
            return workResultService.findByWorkOrderIdAndStatus(workOrderId, status);
        } else if (workOrderId != null && equipmentId != null) {
            return workResultService.findByWorkOrderIdAndEquipmentId(workOrderId, equipmentId);
        } else if (workOrderId != null && workCenterId != null) {
            return workResultService.findByWorkOrderIdAndWorkCenterId(workOrderId, workCenterId);
        } else if (workOrderId != null && employeeId != null) {
            return workResultService.findByWorkOrderIdAndEmployeeId(workOrderId, employeeId);
        }
        // --- 1가지 조건 ---
        else if (start != null && end != null) {
            return workResultService.findByStartTimeBetween(start, end);
        } else if (status != null) {
            return workResultService.findByStatus(status);
        } else if (equipmentId != null) {
            return workResultService.findByEquipmentId(equipmentId);
        } else if (workCenterId != null) {
            return workResultService.findByWorkCenterId(workCenterId);
        } else if (employeeId != null) {
            return workResultService.findByEmployeeId(employeeId);
        } else if (workOrderId != null) {
            return workResultService.findByWorkOrderId(workOrderId);
        }
        // --- 조건 없음 ---
        else {
            return workResultService.findAll();
        }
    }
}
