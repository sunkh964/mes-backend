package com.example.mes_backend.controller;

import com.example.mes_backend.dto.BlockPlanDto;
import com.example.mes_backend.dto.QualityControlDto;
import com.example.mes_backend.service.BlockPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blockPlans")
public class BlockPlanController {

    private final BlockPlanService blockPlanService;

    // 전체 조회
    @GetMapping("/getAll")
    public List<BlockPlanDto> getAllBlockPlan() {

        return blockPlanService.getAll();
    }

    // 조건 검색
    @GetMapping("/search")
    public List<BlockPlanDto> searchBlockPlans(
            @RequestParam(required = false) Integer blockId,
            @RequestParam(required = false) String processId,
            @RequestParam(required = false) String vesselId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Integer status
    ) {
        return blockPlanService.search(blockId, processId, vesselId, startDate, endDate, status);
    }
}
