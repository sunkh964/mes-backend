package com.example.mes_backend.service;

import com.example.mes_backend.dto.MaterialInputDto;
import com.example.mes_backend.repository.MaterialInputRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialInputService {

    private final MaterialInputRepository materialInputRepository;

    // 전체 조회
    public List<MaterialInputDto> findAll() {
        return materialInputRepository.findAll().stream()
                .map(MaterialInputDto::fromEntity)
                .toList();
    }

    // 유틸: LIKE 패턴 (소문자 변환 + escape 처리)
    private String likePattern(String s) {
        if (s == null) return null;
        String escaped = s.trim()
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
        return "%" + escaped.toLowerCase() + "%";
    }

    /**
     * 동적 검색 (resultId, workOrderId, materialId, warehouse, inputDate 범위)
     */
    public List<MaterialInputDto> search(Integer resultId,
                                         Integer workOrderId,
                                         Integer materialId,
                                         String warehouse,
                                         LocalDateTime startDateTime,
                                         LocalDateTime endDateTime) {

        return materialInputRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 작업내역 ID (정확 검색)
            if (resultId != null) {
                predicates.add(cb.equal(root.get("workResult").get("resultId"), resultId));
            }

            // 작업지시 ID (정확 검색)
            if (workOrderId != null) {
                predicates.add(cb.equal(root.get("workOrder").get("workOrderId"), workOrderId));
            }

            // 자재 ID (정확 검색)
            if (materialId != null) {
                predicates.add(cb.equal(root.get("material").get("materialId"), materialId));
            }

            // 창고 (부분검색 LIKE)
            if (warehouse != null && !warehouse.isBlank()) {
                String p = likePattern(warehouse);
                predicates.add(cb.like(cb.lower(root.get("warehouse")), p, '\\'));
            }

            // 사용일 범위
            if (startDateTime != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("inputDate"), startDateTime));
            }
            if (endDateTime != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("inputDate"), endDateTime));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }).stream().map(MaterialInputDto::fromEntity).toList();
    }
}
