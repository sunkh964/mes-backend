package com.example.mes_backend.controller;

import com.example.mes_backend.dto.ShipmentsDto;
import com.example.mes_backend.service.ShipmentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipments")
public class ShipmentsController {

    private final ShipmentsService shipmentsService;

    // 전체 조회
    @GetMapping
    public List<ShipmentsDto> getAllShipment() {
        return shipmentsService.getAll();
    }

    // 조건 검색
    @GetMapping("/search")
    public List<ShipmentsDto> searchShipments(
            @RequestParam(required = false) String salesOrderId,
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String vesselId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate plannedShipDateFrom,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate plannedShipDateTo,
            @RequestParam(required = false) Integer status
    ) {
        LocalDateTime from = plannedShipDateFrom != null ? plannedShipDateFrom.atStartOfDay() : null;
        LocalDateTime to   = plannedShipDateTo != null ? plannedShipDateTo.atTime(23, 59, 59) : null;

        return shipmentsService.search(
                salesOrderId,
                customerId,
                vesselId,
                from,
                to,
                status
        );
    }

    // ----- 등록 -----
    @PostMapping
    public ShipmentsDto createShipment(@RequestBody ShipmentsDto dto) {
        return shipmentsService.create(dto);
    }

    // -----삭제 -----
    @DeleteMapping("/{shipmentId}")
    public void deleteShipment(@PathVariable("shipmentId") String shipmentId) {
        shipmentsService.delete(shipmentId);
    }

    // ------- 수정 -----
    @PutMapping("/{shipmentId}")
    public ShipmentsDto updateShipment(@PathVariable("shipmentId") String shipmentId,
                                       @RequestBody ShipmentsDto dto) {
        return shipmentsService.update(shipmentId, dto);
    }

}
