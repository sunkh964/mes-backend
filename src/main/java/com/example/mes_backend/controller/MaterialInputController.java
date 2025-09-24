package com.example.mes_backend.controller;

import com.example.mes_backend.dto.MaterialInputDto;
import com.example.mes_backend.service.MaterialInputService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // ================= 등록 =================
    @PostMapping
    public MaterialInputDto create(@RequestBody MaterialInputDto dto) {
        return materialInputService.create(dto);
    }

    // ================= 수정 =================
    @PutMapping("/{inputId}")
    public MaterialInputDto update(
            @PathVariable("inputId") int inputId,
            @RequestBody MaterialInputDto dto) {
        return materialInputService.update(inputId, dto);
    }

    // ================= 삭제 =================
    @DeleteMapping("/{inputId}")
    public void delete(@PathVariable("inputId") int inputId) {
        materialInputService.delete(inputId);
    }
}
