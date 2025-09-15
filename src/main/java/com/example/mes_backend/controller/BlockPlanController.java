package com.example.mes_backend.controller;

import com.example.mes_backend.dto.BlockPlanDto;
import com.example.mes_backend.dto.QualityControlDto;
import com.example.mes_backend.service.BlockPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/blockPlans")
public class BlockPlanController {

    private final BlockPlanService blockPlanService;

    // 전체 조회
    @GetMapping
    public List<BlockPlanDto> getAllBlockPlan() {

        return blockPlanService.getAll();
    }
}
