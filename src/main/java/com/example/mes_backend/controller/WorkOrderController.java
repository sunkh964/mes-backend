package com.example.mes_backend.controller;

import com.example.mes_backend.dto.WorkOrderDto;
import com.example.mes_backend.service.WorkOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workOrders")
public class WorkOrderController {

    private final WorkOrderService workOrderService;

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
}
