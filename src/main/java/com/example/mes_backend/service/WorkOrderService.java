package com.example.mes_backend.service;

import com.example.mes_backend.dto.WorkOrderDto;
import com.example.mes_backend.repository.WorkOrderRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    // 조건 검색 (기본 검색)
    public List<WorkOrderDto> search(String processId, Integer blockId, String workCenterId, String currentStatus) {
        return workOrderRepository.findAll((root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();

                    // 공정 ID
                    if (processId != null && !processId.isEmpty()) {
                        predicates.add(cb.equal(root.get("process").get("processId"), processId));
                    }

                    // 블록 ID
                    if (blockId != null) {
                        predicates.add(cb.equal(root.get("block").get("blockId"), blockId));
                    }

                    // 작업장 ID
                    if (workCenterId != null && !workCenterId.isEmpty()) {
                        predicates.add(cb.equal(root.get("workCenter").get("workCenterId"), workCenterId));
                    }

                    // 현재 상태
                    if (currentStatus != null && !currentStatus.isEmpty()) {
                        predicates.add(cb.equal(root.get("currentStatus"), currentStatus));
                    }

                    return cb.and(predicates.toArray(new Predicate[0]));
                }).stream()
                .map(WorkOrderDto::fromEntity)
                .toList();
    }

    // 상세 검색 (추가 옵션 검색)
    public List<WorkOrderDto> searchDetail(String processId, Integer blockId, String workCenterId, String currentStatus,
                                           Integer priority, String plannedStartTime, String plannedEndTime) {
        return workOrderRepository.findAll((root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();

                    // 기본 조건 (1~2순위)
                    if (processId != null && !processId.isEmpty()) {
                        predicates.add(cb.equal(root.get("process").get("processId"), processId));
                    }
                    if (blockId != null) {
                        predicates.add(cb.equal(root.get("block").get("blockId"), blockId));
                    }
                    if (workCenterId != null && !workCenterId.isEmpty()) {
                        predicates.add(cb.equal(root.get("workCenter").get("workCenterId"), workCenterId));
                    }
                    if (currentStatus != null && !currentStatus.isEmpty()) {
                        predicates.add(cb.equal(root.get("currentStatus"), currentStatus));
                    }

                    // 상세 조건
                    if (priority != null) {
                        predicates.add(cb.equal(root.get("priority"), priority));
                    }
                    if (plannedStartTime != null && !plannedStartTime.isEmpty()) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("plannedStartTime"), plannedStartTime));
                    }
                    if (plannedEndTime != null && !plannedEndTime.isEmpty()) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("plannedEndTime"), plannedEndTime));
                    }

                    return cb.and(predicates.toArray(new Predicate[0]));
                }).stream()
                .map(WorkOrderDto::fromEntity)
                .toList();
    }

}
