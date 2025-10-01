package com.example.mes_backend.controller;


import com.example.mes_backend.dto.*;
import com.example.mes_backend.entity.BlockEntity;
import com.example.mes_backend.entity.BlockPlanEntity;
import com.example.mes_backend.entity.Employee;
import com.example.mes_backend.entity.EquipmentEntity;
import com.example.mes_backend.entity.ProcessEntity;
import com.example.mes_backend.entity.WorkCenterEntity;
import com.example.mes_backend.repository.*;
import com.example.mes_backend.service.WorkOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workOrders")
public class WorkOrderController {

    private final WorkOrderService workOrderService;
    private final ProcessRepository processRepository;
    private final BlockRepository blockRepository;
    private final BlockPlanRepository blockPlanRepository;
    private final WorkCenterRepository workCenterRepository;
    private final EquipmentRepository equipmentRepository;
    private final EmployeeRepository employeeRepository;

    // 전체 조회
    @GetMapping
    public List<WorkOrderDto> getAllWorkOrder() {

        return workOrderService.getAll();
    }

    // 기본 조건 검색
    @GetMapping("/search")
    public List<WorkOrderDto> searchWorkOrders(
            @RequestParam(required = false) Integer workOrderId,
            @RequestParam(required = false) String processId,
            @RequestParam(required = false) Integer blockId,
            @RequestParam(required = false) String workCenterId,
            @RequestParam(required = false) Integer priority,
            @RequestParam(required = false) String currentStatus,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime plannedStartTimeFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime plannedStartTimeTo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime plannedEndTimeFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime plannedEndTimeTo
    ) {
        return workOrderService.search(
                workOrderId,
                processId,
                blockId,
                workCenterId,
                plannedStartTimeFrom,
                plannedStartTimeTo,
                plannedEndTimeFrom,
                plannedEndTimeTo,
                priority,
                currentStatus
        );
    }

    // 등록
    @PostMapping
    public WorkOrderDto createWorkOrder(@RequestBody WorkOrderDto dto) {
        return workOrderService.create(dto);
    }

    //  수정
    @PutMapping("/{workOrderId}")
    public WorkOrderDto updateWorkOrder(
            @PathVariable int workOrderId,
            @RequestBody WorkOrderDto dto
    ) {
        return workOrderService.update(workOrderId, dto);
    }

    //  삭제
    @DeleteMapping("/{workOrderId}")
    public void deleteWorkOrder(@PathVariable int workOrderId) {
        workOrderService.delete(workOrderId);
    }

    // === 콤보박스 데이터 제공 ===
    @GetMapping("/processes")
    public List<ProcessDto> getProcesses() {
        return workOrderService.getAllProcesses();
    }

    @GetMapping("/blocks")
    public List<BlockDto> getBlocks() {
        return workOrderService.getAllBlocks();
    }

    @GetMapping("/blockPlans")
    public List<BlockPlanDto> getBlockPlans() {
        return workOrderService.getAllBlockPlans();
    }

    @GetMapping("/workCenters")
    public List<WorkCenterDto> getWorkCenters() {
        return workOrderService.getAllWorkCenters();
    }

    @GetMapping("/equipments")
    public List<EquipmentDto> getEquipments() {
        return workOrderService.getAllEquipments();
    }

    @GetMapping("/employees")
    public List<EmployeeDto> getEmployees() {
        return workOrderService.getAllEmployees();
    }
}
