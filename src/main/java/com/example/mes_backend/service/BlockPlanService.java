package com.example.mes_backend.service;

import com.example.mes_backend.dto.BlockPlanDto;
import com.example.mes_backend.repository.BlockPlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;


@Slf4j
@Service
@RequiredArgsConstructor
public class BlockPlanService {

    private final BlockPlanRepository blockPlanRepository;

    // 전체 조회
    public List<BlockPlanDto> getAll() {
        return blockPlanRepository.findAll()
                .stream()
                // Dto 변환 메소드
                .map(BlockPlanDto::fromEntity)
                .toList();
    }

    // LIKE 패턴 유틸 (소문자 변환 + escape 처리)
    private String likePattern(String s) {
        if (s == null) return null;
        String escaped = s.trim()
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
        return "%" + escaped.toLowerCase() + "%";
    }

    // 조건 검색 (모든 조건을 부분검색)
    public List<BlockPlanDto> search(String blockId,
                                     String processId,
                                     String vesselId,
                                     LocalDate startDate,
                                     LocalDate endDate,
                                     String status) {

        return blockPlanRepository.findAll((root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();

                    // 블록 ID (부분검색)
                    if (blockId != null && !blockId.isBlank()) {
                        String p = likePattern(blockId);
                        predicates.add(cb.like(cb.lower(root.get("block").get("blockId").as(String.class)), p, '\\'));
                    }

                    // 공정 ID (부분검색)
                    if (processId != null && !processId.isBlank()) {
                        String p = likePattern(processId);
                        predicates.add(cb.like(cb.lower(root.get("process").get("processId").as(String.class)), p, '\\'));
                    }

                    // 선박 ID (부분검색)
                    if (vesselId != null && !vesselId.isBlank()) {
                        String p = likePattern(vesselId);
                        predicates.add(cb.like(cb.lower(root.get("vessel").get("vesselId").as(String.class)), p, '\\'));
                    }

                    // 시작일 (>=)
                    if (startDate != null) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), startDate));
                    }

                    // 종료일 (<=)
                    if (endDate != null) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("endDate"), endDate));
                    }

                    // 상태 (부분검색)
                    if (status != null && !status.isBlank()) {
                        String p = likePattern(status);
                        predicates.add(cb.like(cb.lower(root.get("status").as(String.class)), p, '\\'));
                    }

                    return cb.and(predicates.toArray(new Predicate[0]));
                }).stream()
                .map(BlockPlanDto::fromEntity)
                .toList();
    }

}
