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

    // 조건 검색 Specification(동적 쿼리 작성 패턴)
    public List<BlockPlanDto> search(Integer blockId, String processId, String vesselId,
                                     LocalDate startDate, LocalDate endDate, Integer status) {

        return blockPlanRepository.findAll((root, query, cb) -> {
                    // Predicate는 Criteria API에서 WHERE 조건을 표현하는 객체
                    //여러 개의 조건을 List<Predicate>로 모아두고 → cb.and()로 합쳐서 반환
                    List<Predicate> predicates = new ArrayList<>(); // WHERE 조건들을 담는 리스트

                    // 블록 ID 조건
                    if (blockId != null) {
                        predicates.add(cb.equal(root.get("blockEntity").get("blockId"), blockId));
                    }

                    // 공정 ID 조건
                    if (processId != null && !processId.isEmpty()) {
                        predicates.add(cb.equal(root.get("process").get("processId"), processId));
                    }

                    // 선박 ID 조건
                    if (vesselId != null && !vesselId.isEmpty()) {
                        predicates.add(cb.equal(root.get("vesselEntity").get("vesselId"), vesselId));
                    }

                    // 시작일 조건 (start_date >= 파라미터)
                    if (startDate != null) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), startDate));
                    }

                    // 종료일 조건 (end_date <= 파라미터)
                    if (endDate != null) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("endDate"), endDate));
                    }

                    // 상태 조건
                    if (status != null) {
                        predicates.add(cb.equal(root.get("status"), status));
                    }

                    // AND 조건으로 조합
                    return cb.and(predicates.toArray(new Predicate[0]));
                }).stream()
                .map(BlockPlanDto::fromEntity) // Entity → DTO 변환
                .toList();
    }

}
