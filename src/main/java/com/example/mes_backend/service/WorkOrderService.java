package com.example.mes_backend.service;

import com.example.mes_backend.dto.*;
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

                    if (workOrderId != null) {
                        predicates.add(cb.equal(root.get("workOrderId"), workOrderId));
                    }
                    if (processId != null && !processId.isBlank()) {
                        String p = likePattern(processId);
                        predicates.add(cb.like(cb.lower(root.get("process").get("processId").as(String.class)), p, '\\'));
                    }
                    if (blockId != null) {
                        predicates.add(cb.equal(root.get("block").get("blockId"), blockId));
                    }
                    if (workCenterId != null && !workCenterId.isBlank()) {
                        String p = likePattern(workCenterId);
                        predicates.add(cb.like(cb.lower(root.get("workCenter").get("workCenterId").as(String.class)), p, '\\'));
                    }
                    if (plannedStartTimeFrom != null) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("plannedStartTime"), plannedStartTimeFrom));
                    }
                    if (plannedStartTimeTo != null) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("plannedStartTime"), plannedStartTimeTo));
                    }
                    if (plannedEndTimeFrom != null) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("plannedEndTime"), plannedEndTimeFrom));
                    }
                    if (plannedEndTimeTo != null) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("plannedEndTime"), plannedEndTimeTo));
                    }
                    if (priority != null) {
                        predicates.add(cb.equal(root.get("priority"), priority));
                    }
                    if (currentStatus != null && !currentStatus.isBlank()) {
                        String p = likePattern(currentStatus);
                        predicates.add(cb.like(cb.lower(root.get("currentStatus").as(String.class)), p, '\\'));
                    }

                    return cb.and(predicates.toArray(new Predicate[0]));
                }).stream()
                .map(WorkOrderDto::fromEntity)
                .toList();
    }

    // 등록
    @Transactional
    public WorkOrderDto create(WorkOrderDto dto) {
        WorkOrderEntity entity = dto.toEntity();

        // === 연관 엔티티 매핑 ===
        if (dto.getProcessId() != null) {
            ProcessEntity process = processRepository.findById(dto.getProcessId())
                    .orElseThrow(() -> new IllegalArgumentException("공정이 존재하지 않습니다: " + dto.getProcessId()));
            entity.setProcess(process);
        }
        if (dto.getBlockPlanId() != null) {
            BlockPlanEntity blockPlan = blockPlanRepository.findById(dto.getBlockPlanId())
                    .orElseThrow(() -> new IllegalArgumentException("블록계획이 존재하지 않습니다: " + dto.getBlockPlanId()));
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

        WorkOrderEntity saved = workOrderRepository.save(entity);
        return WorkOrderDto.fromEntity(saved);
    }

    // 수정
    @Transactional
    public WorkOrderDto update(int workOrderId, WorkOrderDto dto) {
        return workOrderRepository.findById(workOrderId)
                .map(entity -> {
                    if (dto.getProcessId() != null) {
                        ProcessEntity process = processRepository.findById(dto.getProcessId())
                                .orElseThrow(() -> new IllegalArgumentException("공정이 존재하지 않습니다: " + dto.getProcessId()));
                        entity.setProcess(process);
                    }
                    if (dto.getBlockPlanId() != null) {
                        BlockPlanEntity blockPlan = blockPlanRepository.findById(dto.getBlockPlanId())
                                .orElseThrow(() -> new IllegalArgumentException("블록계획이 존재하지 않습니다: " + dto.getBlockPlanId()));
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


    // ================= 콤보박스 =================
    public List<ProcessDto> getAllProcesses() {
        return processRepository.findAll()
                .stream()
                .map(ProcessDto::fromEntity)
                .toList();
    }

    public List<BlockDto> getAllBlocks() {
        return blockRepository.findAll()
                .stream()
                .map(BlockDto::fromEntity)
                .toList();
    }

    public List<BlockPlanDto> getAllBlockPlans() {
        return blockPlanRepository.findAll()
                .stream()
                .map(BlockPlanDto::fromEntity)
                .toList();
    }

    public List<WorkCenterDto> getAllWorkCenters() {
        return workCenterRepository.findAll()
                .stream()
                .map(WorkCenterDto::fromEntity)
                .toList();
    }

    public List<EquipmentDto> getAllEquipments() {
        return equipmentRepository.findAll()
                .stream()
                .map(EquipmentDto::fromEntity)
                .toList();
    }

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeDto::fromEntity)
                .toList();
    }

}
