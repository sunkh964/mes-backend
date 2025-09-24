package com.example.mes_backend.service;

import com.example.mes_backend.dto.BlockPlanDto;
import com.example.mes_backend.dto.ProcessDto;
import com.example.mes_backend.entity.*;
import com.example.mes_backend.repository.*;
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
    private final VesselRepository vesselRepository;
    private final ProcessRepository processRepository;
    private final BlockRepository blockRepository;
    private final WorkOrderRepository workOrderRepository;

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

    // ========= 등록=========
    @Transactional
    public BlockPlanDto create(BlockPlanDto dto) {
        // DTO → Entity (PK만 매핑된 연관 엔티티 포함)
        BlockPlanEntity entity = dto.toEntity();

        // 저장
        BlockPlanEntity saved = blockPlanRepository.save(entity);

        // === WorkOrder 자동 생성 ===
        WorkOrderEntity wo = new WorkOrderEntity();
        wo.setProcess(saved.getProcess());
        wo.setBlockPlan(saved);
        wo.setBlock(saved.getBlockEntity());
        wo.setQuantityToProduce(saved.getPlanQty());
        wo.setQuantityProduced(0);
        wo.setPlannedStartTime(saved.getStartDate().atStartOfDay());
        wo.setPlannedEndTime(saved.getEndDate().atTime(23, 59, 59));
        wo.setCurrentStatus("waiting");
        wo.setPriority(0);
        wo.setRemark("BlockPlan 기반 자동 생성");

        workOrderRepository.save(wo);

        return BlockPlanDto.fromEntity(saved);
    }

    // ========= 삭제 ==========
    public void delete(int blockPlanId) {
        if (!blockPlanRepository.existsById(blockPlanId)) {
            throw new IllegalArgumentException("삭제할 블록 생산 계획이 존재하지 않습니다: " + blockPlanId);
        }
        blockPlanRepository.deleteById(blockPlanId);
    }

    // ========= 수정 =========
    @Transactional
    public BlockPlanDto update(int blockPlanId, BlockPlanDto dto) {
        return blockPlanRepository.findById(blockPlanId)
                .map(entity -> {
                    // FK 매핑된 엔티티 조회
                    VesselEntity vessel = vesselRepository.findById(dto.getVesselId())
                            .orElseThrow(() -> new IllegalArgumentException("선박이 존재하지 않습니다: " + dto.getVesselId()));

                    ProcessEntity process = processRepository.findById(dto.getProcessId())
                            .orElseThrow(() -> new IllegalArgumentException("공정이 존재하지 않습니다: " + dto.getProcessId()));

                    BlockEntity block = blockRepository.findById(dto.getBlockId())
                            .orElseThrow(() -> new IllegalArgumentException("블록이 존재하지 않습니다: " + dto.getBlockId()));

                    // 연관 엔티티 세팅
                    entity.setVesselEntity(vessel);
                    entity.setProcess(process);
                    entity.setBlockEntity(block);

                    // 나머지 일반 필드 세팅
                    entity.setPlanQty(dto.getPlanQty());
                    entity.setStatus(dto.getStatus());
                    entity.setStartDate(dto.getStartDate());
                    entity.setEndDate(dto.getEndDate());
                    entity.setRemark(dto.getRemark());

                    return BlockPlanDto.fromEntity(blockPlanRepository.save(entity));
                })
                .orElseThrow(() -> new IllegalArgumentException("수정할 블록 생산 계획이 존재하지 않습니다: " + blockPlanId));
    }



}
