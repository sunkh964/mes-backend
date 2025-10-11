package com.example.mes_backend.service;

import com.example.mes_backend.dto.BlockDto;
import com.example.mes_backend.dto.BlockPlanDto;
import com.example.mes_backend.dto.ProcessDto;
import com.example.mes_backend.entity.*;
import com.example.mes_backend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
@RequiredArgsConstructor
public class BlockPlanService {

    private final BlockPlanRepository blockPlanRepository;
    private final VesselRepository vesselRepository;
//    private final ProjectPlanRepository projectPlanRepository;
    private final ProcessRepository processRepository;
    private final BlockRepository blockRepository;
    private final WorkOrderRepository workOrderRepository;
    private final ErpPlanReferenceRepository erpPlanReferenceRepository;


    private final RestTemplate restTemplate; // ✅ API 서버 호출용
    private final String API_SERVER_URL = "http://localhost:8083/api/proxy/project_plans";

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
                                     String planId,
                                     LocalDate startDate,
                                     LocalDate endDate,
                                     String status) {

        // planId만 넘어온 경우는 fetch join 버전 사용
        if (planId != null && !planId.isBlank()
                && (blockId == null || blockId.isBlank())
                && (processId == null || processId.isBlank())
                && startDate == null && endDate == null
                && (status == null || status.isBlank())) {
            return blockPlanRepository.findByErpPlanReference_PlanIdWithJoins(planId) //
                    .stream()
                    .map(BlockPlanDto::fromEntity)
                    .toList();
        }

        // 조건이 섞여 있을 때는 기존 CriteriaBuilder 사용

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

                    // 프로젝트 생산계획 ID (join 사용)
                    if (planId != null && !planId.isBlank()) {
                        // root.get("erpPlanReference")를 통해 연관 엔티티에 접근하고, 그 안의 planId 필드를 비교
                        predicates.add(cb.equal(root.get("erpPlanReference").get("planId"), planId));
                    }

//                    if (planId != null && !planId.isBlank()) {
//                        Join<BlockPlanEntity, ProjectPlanEntity> projectPlanJoin = root.join("projectPlanEntity");
//                        predicates.add(cb.equal(projectPlanJoin.get("planId"), planId));
//                    }

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
        // 1. DTO에서 받은 planId로 erp_plan_references 테이블을 조회하여 엔티티를 찾습니다.
        ErpPlanReference planRef = erpPlanReferenceRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "유효하지 않은 ERP 생산 계획 ID입니다. ID가 MES에 동기화되었는지 확인하세요: " + dto.getPlanId()
                ));

        // 2. 다른 연관 엔티티들도 조회합니다.
        ProcessEntity process = processRepository.findById(dto.getProcessId())
                .orElseThrow(() -> new EntityNotFoundException("공정을 찾을 수 없습니다: " + dto.getProcessId()));
        BlockEntity block = blockRepository.findById(dto.getBlockId())
                .orElseThrow(() -> new EntityNotFoundException("블록을 찾을 수 없습니다: " + dto.getBlockId()));

        // 3. 새로운 BlockPlanEntity를 생성하고 DTO의 값들을 채웁니다.
        BlockPlanEntity newEntity = new BlockPlanEntity();
        newEntity.setPlanQty(dto.getPlanQty());
        newEntity.setStartDate(dto.getStartDate());
        newEntity.setEndDate(dto.getEndDate());
        newEntity.setStatus(dto.getStatus());
        newEntity.setRemark(dto.getRemark());

        // 4. 조회한 엔티티 '객체'들을 설정합니다. (String이 아님!)
        newEntity.setErpPlanReference(planRef); // ✅ 여기가 핵심!
        newEntity.setProcess(process);
        newEntity.setBlockEntity(block);

        // 5. 저장합니다. 이제 JPA가 올바른 외래 키 관계를 맺어줍니다.
        BlockPlanEntity saved = blockPlanRepository.save(newEntity);

//        // === WorkOrder 자동 생성 ===
//        WorkOrderEntity wo = new WorkOrderEntity();
//        wo.setProcess(saved.getProcess());
//        wo.setBlockPlan(saved);
//        wo.setBlock(saved.getBlockEntity());
//        wo.setQuantityToProduce(saved.getPlanQty());
//        wo.setQuantityProduced(0);
//        wo.setPlannedStartTime(saved.getStartDate().atStartOfDay());
//        wo.setPlannedEndTime(saved.getEndDate().atTime(23, 59, 59));
//        wo.setCurrentStatus("waiting");
//        wo.setPriority(0);
//        wo.setRemark("BlockPlan 기반 자동 생성");
//
//        workOrderRepository.save(wo);

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
//    @Transactional
//    public BlockPlanDto update(int blockPlanId, BlockPlanDto dto) {
//        return blockPlanRepository.findById(blockPlanId)
//                .map(entity -> {
//                    // FK 매핑된 엔티티 조회
////                    VesselEntity vessel = vesselRepository.findById(dto.getVesselId())
////                            .orElseThrow(() -> new IllegalArgumentException("선박이 존재하지 않습니다: " + dto.getVesselId()));
//
////                    ProjectPlanEntity projectPlan = projectPlanRepository.findById(dto.getPlanId())
////                            .orElseThrow(() -> new IllegalArgumentException("프로젝트 생산 계획이 존재하지 않습니다: " + dto.getPlanId()));
//
//                    ProcessEntity process = processRepository.findById(dto.getProcessId())
//                            .orElseThrow(() -> new IllegalArgumentException("공정이 존재하지 않습니다: " + dto.getProcessId()));
//
//                    BlockEntity block = blockRepository.findById(dto.getBlockId())
//                            .orElseThrow(() -> new IllegalArgumentException("블록이 존재하지 않습니다: " + dto.getBlockId()));
//
//                    // 연관 엔티티 세팅
////                    entity.setVesselEntity(vessel);
////                    entity.setProjectPlanEntity(projectPlan);
//                    entity.setProcess(process);
//                    entity.setBlockEntity(block);
//
//                    // 나머지 일반 필드 세팅
//                    entity.setPlanId(dto.getPlanId());
//                    entity.setPlanQty(dto.getPlanQty());
//                    entity.setStatus(dto.getStatus());
//                    entity.setStartDate(dto.getStartDate());
//                    entity.setEndDate(dto.getEndDate());
//                    entity.setRemark(dto.getRemark());
//
//                    return BlockPlanDto.fromEntity(blockPlanRepository.save(entity));
//                })
//                .orElseThrow(() -> new IllegalArgumentException("수정할 블록 생산 계획이 존재하지 않습니다: " + blockPlanId));
//    }
    @Transactional
    public BlockPlanDto update(int blockPlanId, BlockPlanDto dto) {
        return blockPlanRepository.findById(blockPlanId)
                .map(entity -> {
                    try {
                        // 연관 엔티티 조회
                        ProcessEntity process = processRepository.findById(dto.getProcessId())
                                .orElseThrow(() -> new IllegalArgumentException("공정이 존재하지 않습니다: " + dto.getProcessId()));

                        BlockEntity block = blockRepository.findById(dto.getBlockId())
                                .orElseThrow(() -> new IllegalArgumentException("블록이 존재하지 않습니다: " + dto.getBlockId()));

                        // 연관 엔티티 세팅
                        entity.setProcess(process);
                        entity.setBlockEntity(block);

                        // 일반 필드 세팅
                        entity.setPlanQty(dto.getPlanQty());
                        entity.setStatus(dto.getStatus());
                        entity.setStartDate(dto.getStartDate());
                        entity.setEndDate(dto.getEndDate());
                        entity.setRemark(dto.getRemark());

                        // 저장
                        BlockPlanEntity saved = blockPlanRepository.save(entity);

                        // ===================== 진행률 업데이트 =====================
                        List<BlockPlanEntity> blocks = blockPlanRepository.findByErpPlanReference_PlanIdWithJoins(dto.getPlanId());

                        if (!blocks.isEmpty()) {
                            long completed = blocks.stream()
                                    .filter(bp -> bp.getStatus() != null && bp.getStatus() == 2) // 2 = 완료
                                    .count();

                            BigDecimal progressRate = BigDecimal.valueOf(completed * 100.0 / blocks.size())
                                    .setScale(2, RoundingMode.HALF_UP);

                            log.info("[MES] ProjectPlan({}) 진행률 계산됨 = {}%", dto.getPlanId(), progressRate);

                            try {
                                HttpHeaders headers = new HttpHeaders();
                                headers.setContentType(MediaType.APPLICATION_JSON);

                                // Body에 progressRate(BigDecimal) 담기
                                HttpEntity<BigDecimal> entityReq = new HttpEntity<>(progressRate, headers);

                                // API 서버(8083)에 PUT 요청
                                restTemplate.exchange(
                                        API_SERVER_URL + "/" + dto.getPlanId() + "/progress",
                                        HttpMethod.PUT,
                                        entityReq,
                                        Void.class
                                );

                                log.info("API 서버로 진행률 {}% 업데이트 성공", progressRate);

                            } catch (Exception e) {
                                log.error("API 서버로 진행률 업데이트 요청 실패", e);
                                throw new RuntimeException("API 서버로 진행률 업데이트 요청 실패", e);
                            }
                        }
                        // ==========================================================

                        return BlockPlanDto.fromEntity(saved);

                    } catch (Exception e) {
                        log.error("BlockPlan 업데이트 중 오류 발생", e);
                        throw e;
                    }
                })
                .orElseThrow(() -> new IllegalArgumentException("수정할 블록 생산 계획이 존재하지 않습니다: " + blockPlanId));
    }



    // 콤보박스
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

// ==================================================================

    // 진행률 업데이트 메서드
//    @Transactional
//    public void updateProjectProgress(String planId) {
//        // fetch join 버전 사용
//        List<BlockPlanEntity> blocks = blockPlanRepository.findByPlanIdWithJoins(planId);
//
//        if (blocks.isEmpty()) return;
//
//        long completed = blocks.stream()
//                .filter(bp -> bp.getStatus() != null && bp.getStatus() == 2) // 2=완료
//                .count();
//
//        BigDecimal progressRate = BigDecimal.valueOf(completed * 100.0 / blocks.size())
//                .setScale(2, RoundingMode.HALF_UP);
//
//        // 계산되나?
//        log.info("👉 [MES] ProjectPlan({}) 진행률 계산됨 = {}%", planId, progressRate);
//
//        // API 서버 호출 (진행률 반영)
//        try {
//            restTemplate.put(API_SERVER_URL + "/" + planId + "/progress", progressRate);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("API 서버로 진행률 업데이트 요청 실패", e);
//        }
//    }



}