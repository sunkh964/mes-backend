package com.example.mes_backend.controller;

import com.example.mes_backend.dto.*;
import com.example.mes_backend.service.BlockPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(required = false) String blockId,
            @RequestParam(required = false) String processId,
            @RequestParam(required = false) String planId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String status
    ) {
        return blockPlanService.search(blockId, processId, planId, startDate, endDate, status);
    }

    // ============= 등록 =================
    @PostMapping
    public BlockPlanDto createBlockPlan(@RequestBody BlockPlanDto dto) {

        return blockPlanService.create(dto);
    }

    // ============= 삭제 =================
    // 삭제
    @DeleteMapping("/{blockPlanId}")
    public void deleteBlockPlan(@PathVariable("blockPlanId") int blockPlanId) {

        blockPlanService.delete(blockPlanId);
    }

    // 수정
    @PutMapping("/{blockPlanId}")
    public BlockPlanDto updateBlockPlan(
            @PathVariable("blockPlanId") int blockPlanId,
            @RequestBody BlockPlanDto dto) {
        return blockPlanService.update(blockPlanId, dto);
    }

    // ============= 콤보박스 데이터 =================
    // 공정 목록
    @GetMapping("/processes")
    public List<ProcessDto> getProcesses() {
        return blockPlanService.getAllProcesses();
    }

    // 블록 목록
    @GetMapping("/blocks")
    public List<BlockDto> getBlocks() {
        return blockPlanService.getAllBlocks();
    }


}