package com.example.mes_backend.service;

import com.example.mes_backend.dto.BlockPlanDto;
import com.example.mes_backend.dto.QualityControlDto;
import com.example.mes_backend.repository.BlockPlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlockPlanService {

    private final BlockPlanRepository blockPlanRepository;

    // 전체 조회
    public List<BlockPlanDto> getAll() {
        return blockPlanRepository.findAll()
                .stream()
                .map(BlockPlanDto::fromEntity)
                .toList();
    }
}
