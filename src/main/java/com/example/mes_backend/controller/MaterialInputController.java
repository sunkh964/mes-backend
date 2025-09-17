package com.example.mes_backend.controller;

import com.example.mes_backend.dto.MaterialInputDto;
import com.example.mes_backend.service.MaterialInputService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/materials-usage")
@RequiredArgsConstructor
public class MaterialInputController {

    private final MaterialInputService materialInputService;

    @GetMapping
    public List<MaterialInputDto> getMaterialInputs(
            @RequestParam(required = false) Integer resultId,   // 작업내역 ID
            @RequestParam(required = false) Integer workOrderId,// 작업지시 ID
            @RequestParam(required = false) Integer materialId, // 자재 ID
            @RequestParam(required = false) String warehouse,   // 창고
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        // LocalDate → LocalDateTime 변환
        LocalDateTime startDateTime = (start != null) ? start.atStartOfDay() : null;
        LocalDateTime endDateTime   = (end != null) ? end.atTime(23, 59, 59) : null;

        return materialInputService.search(
                resultId,
                workOrderId,
                materialId,
                warehouse,
                startDateTime,
                endDateTime
        );
    }
}
