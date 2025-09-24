package com.example.mes_backend.service;

import com.example.mes_backend.dto.WorkCenterDto;
import com.example.mes_backend.dto.WorkOrderDto;
import com.example.mes_backend.entity.*;
import com.example.mes_backend.repository.*;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
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
    private final ProcessRepository processRepository;
    private final BlockPlanRepository blockPlanRepository;
    private final BlockRepository blockRepository;
    private final WorkCenterRepository workCenterRepository;
    private final EquipmentRepository equipmentRepository;
    private final EmployeeRepository employeeRepository;

    // 전체 조회
    public List<WorkOrderDto> getAll() {
        return workOrderRepository.findAllWithRelations()
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

    // 등록
    public WorkOrderDto create(WorkOrderDto dto){
        if(workOrderRepository.existsById(dto.getWorkOrderId())){
            throw new IllegalArgumentException("이미 존재하는 작업지시입니다: " + dto.getWorkOrderId());
        }
        return WorkOrderDto.fromEntity(workOrderRepository.save(dto.toEntity()));
    }

    // 수정
    @Transactional
    public WorkOrderDto update(int workOrderId, WorkOrderDto dto) {
        return workOrderRepository.findById(workOrderId)
                .map(entity -> {
                    // === 연관 엔티티 조회 ===
                    if (dto.getProcessId() != null) {
                        ProcessEntity process = processRepository.findById(dto.getProcessId())
                                .orElseThrow(() -> new IllegalArgumentException("공정이 존재하지 않습니다: " + dto.getProcessId()));
                        entity.setProcess(process);
                    }

                    if (dto.getBlockPlanId() != null) {
                        BlockPlanEntity blockPlan = blockPlanRepository.findById(dto.getBlockPlanId())
                                .orElseThrow(() -> new IllegalArgumentException("블록 계획이 존재하지 않습니다: " + dto.getBlockPlanId()));
                        entity.setBlockPlan(blockPlan);
                    }

                    if (dto.getBlockId() != null) {
                        BlockEntity block = blockRepository.findById(dto.getBlockId())
                                .orElseThrow(() -> new IllegalArgumentException("블록이 존재하지 않습니다: " + dto.getBlockId()));
                        entity.setBlock(block);
                    }

                    if (dto.getWorkCenterId() != null) {
                        WorkCenterEntity wc = workCenterRepository.findById(dto.getWorkCenterId())
                                .orElseThrow(() -> new IllegalArgumentException("작업장이 존재하지 않습니다: " + dto.getWorkCenterId()));
                        entity.setWorkCenter(wc);
                    }

                    if (dto.getEquipmentId() != null) {
                        EquipmentEntity eq = equipmentRepository.findById(dto.getEquipmentId())
                                .orElseThrow(() -> new IllegalArgumentException("설비가 존재하지 않습니다: " + dto.getEquipmentId()));
                        entity.setEquipment(eq);
                    }

                    if (dto.getEmployeeId() != null) {
                        Employee emp = employeeRepository.findById(dto.getEmployeeId())
                                .orElseThrow(() -> new IllegalArgumentException("작업자가 존재하지 않습니다: " + dto.getEmployeeId()));
                        entity.setEmployee(emp);
                    }

                    // === 단순 필드 업데이트 ===
                    entity.setInstruction(dto.getInstruction());
                    entity.setQuantityToProduce(dto.getQuantityToProduce());
                    entity.setQuantityProduced(dto.getQuantityProduced());
                    entity.setPlannedStartTime(dto.getPlannedStartTime());
                    entity.setPlannedEndTime(dto.getPlannedEndTime());
                    entity.setActualStartTime(dto.getActualStartTime());
                    entity.setActualEndTime(dto.getActualEndTime());
                    entity.setCurrentStatus(dto.getCurrentStatus());
                    entity.setPriority(dto.getPriority());
                    entity.setRemark(dto.getRemark());

                    return WorkOrderDto.fromEntity(workOrderRepository.save(entity));
                })
                .orElseThrow(() -> new IllegalArgumentException("수정할 작업지시가 존재하지 않습니다: " + workOrderId));
    }



    // 삭제
    public void delete(int workOrderId) {
        if (!workOrderRepository.existsById(workOrderId)) {
            throw new IllegalArgumentException("삭제할 작업지시가 존재하지 않습니다: " + workOrderId);
        }
        workOrderRepository.deleteById(workOrderId);
    }
}
