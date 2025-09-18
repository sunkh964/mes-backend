package com.example.mes_backend.service;

import com.example.mes_backend.dto.WorkOrderDto;
import com.example.mes_backend.repository.WorkOrderRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkOrderService {

    private final WorkOrderRepository workOrderRepository;

    // 전체 조회
    public List<WorkOrderDto> getAll() {
        return workOrderRepository.findAll()
                .stream()
                .map(WorkOrderDto::fromEntity)
                .toList();
    }

    // LIKE 패턴 유틸
    private String likePattern(String s) {
        if (s == null) return null;
        String escaped = s.trim()
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
        return "%" + escaped.toLowerCase() + "%";
    }

    // 조건 검색
    public List<WorkOrderDto> search(Integer workOrderId,
                                     String processId,
                                     Integer blockId,
                                     String workCenterId,
                                     LocalDateTime plannedStartTimeFrom,
                                     LocalDateTime plannedStartTimeTo,
                                     LocalDateTime plannedEndTimeFrom,
                                     LocalDateTime plannedEndTimeTo,
                                     Integer priority,
                                     String currentStatus) {

        return workOrderRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 작업지시 ID
            if (workOrderId != null) {
                predicates.add(cb.equal(root.get("workOrderId"), workOrderId));
            }

            // 공정 ID (부분검색)
            if (processId != null && !processId.isBlank()) {
                String p = likePattern(processId);
                predicates.add(cb.like(cb.lower(root.get("process").get("processId").as(String.class)), p, '\\'));
            }

            // 블록 ID
            if (blockId != null) {
                predicates.add(cb.equal(root.get("block").get("blockId"), blockId));
            }

            // 작업장 ID (부분검색)
            if (workCenterId != null && !workCenterId.isBlank()) {
                String p = likePattern(workCenterId);
                predicates.add(cb.like(cb.lower(root.get("workCenter").get("workCenterId").as(String.class)), p, '\\'));
            }

            // 계획 시작일
            if (plannedStartTimeFrom != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.<LocalDateTime>get("plannedStartTime"), plannedStartTimeFrom));
            }
            if (plannedStartTimeTo != null) {
                predicates.add(cb.lessThanOrEqualTo(root.<LocalDateTime>get("plannedStartTime"), plannedStartTimeTo));
            }

            // 계획 종료일
            if (plannedEndTimeFrom != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.<LocalDateTime>get("plannedEndTime"), plannedEndTimeFrom));
            }
            if (plannedEndTimeTo != null) {
                predicates.add(cb.lessThanOrEqualTo(root.<LocalDateTime>get("plannedEndTime"), plannedEndTimeTo));
            }

            // 우선순위
            if (priority != null) {
                predicates.add(cb.equal(root.get("priority"), priority));
            }

            // 현재 상태 (부분검색)
            if (currentStatus != null && !currentStatus.isBlank()) {
                String p = likePattern(currentStatus);
                predicates.add(cb.like(cb.lower(root.get("currentStatus").as(String.class)), p, '\\'));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }).stream().map(WorkOrderDto::fromEntity).toList();
    }
}
