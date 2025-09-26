package com.example.mes_backend.service;

import com.example.mes_backend.dto.MaterialInputDto;
import com.example.mes_backend.dto.StockRequestDto;
import com.example.mes_backend.entity.*;
import com.example.mes_backend.repository.MaterialInputRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialInputService {

    private final MaterialInputRepository materialInputRepository;
//    private final MaterialRepository materialRepository;

    private final RestTemplate restTemplate; // ERP API 호출용


    @PersistenceContext   // PA가 자동으로 주입
    private EntityManager entityManager;

    //private final String ERP_BASE_URL = "http://localhost:8081/api/inventory";
    private final String ERP_BASE_URL = "http://localhost:8081";


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

    // ======== 등록
    @Transactional
    public MaterialInputDto create(MaterialInputDto dto) {
        MaterialInputEntity entity = dto.toEntity();

        // WorkResult FK 연결
        if (dto.getResultId() != null) {
            WorkResultEntity workResult = entityManager.getReference(WorkResultEntity.class, dto.getResultId());
            entity.setWorkResult(workResult);
        }

        // WorkOrder FK 연결
        if (dto.getWorkOrderId() != null) {
            WorkOrderEntity workOrder = entityManager.getReference(WorkOrderEntity.class, dto.getWorkOrderId());
            entity.setWorkOrder(workOrder);
        }

//        // Material FK 연결 (재고 차감 로직 때문에 실제 조회)
//        MaterialEntity material = materialRepository.findById(dto.getMaterialId())
//                .orElseThrow(() -> new IllegalArgumentException("자재 없음: " + dto.getMaterialId()));
//        entity.setMaterial(material);

        // Employee FK 연결
        if (dto.getEmployeeId() != null) {
            Employee employee = entityManager.getReference(Employee.class, dto.getEmployeeId());
            entity.setEmployee(employee);
        }

        // ERP API 호출 - 재고 차감
        StockRequestDto req = new StockRequestDto(dto.getMaterialId(), dto.getWarehouse(), dto.getLocation(), dto.getQuantity());
        restTemplate.postForEntity(ERP_BASE_URL + "/api/inventory/deduct", req, Void.class);

        MaterialInputEntity saved = materialInputRepository.save(entity);
        return MaterialInputDto.fromEntity(saved);
    }

    // =========== 수정
    @Transactional
    public MaterialInputDto update(Integer inputId, MaterialInputDto dto) {
        MaterialInputEntity existing = materialInputRepository.findById(inputId)
                .orElseThrow(() -> new IllegalArgumentException("해당 투입 내역을 찾을 수 없습니다. ID=" + inputId));

        int oldQty = existing.getQuantity();
        int newQty = dto.getQuantity();
        int diff = newQty - oldQty;

        // ERP 연동 (차이만큼 증/차감)
        if (diff > 0) {
            // 더 많이 사용 → ERP 차감
            StockRequestDto req = new StockRequestDto(dto.getMaterialId(), dto.getWarehouse(), dto.getLocation(), diff);
            restTemplate.postForEntity(ERP_BASE_URL + "/api/inventory/deduct", req, Void.class);
        } else if (diff < 0) {
            // 덜 사용 → ERP 복구
            StockRequestDto req = new StockRequestDto(dto.getMaterialId(), dto.getWarehouse(), dto.getLocation(), Math.abs(diff));
            restTemplate.postForEntity(ERP_BASE_URL + "/api/inventory/restore", req, Void.class);
        }

//        // ERP API 호출 - 재고 수정
//        StockRequestDto req = new StockRequestDto(dto.getMaterialId(), dto.getWarehouse(), dto.getLocation(), dto.getQuantity());
//        restTemplate.put(ERP_BASE_URL + "/api/inventory/update", req);

        // 엔티티 값 갱신
        existing.setQuantity(dto.getQuantity());
        existing.setQuantity(dto.getQuantity());
        existing.setUnit(dto.getUnit());
        existing.setWarehouse(dto.getWarehouse());
        existing.setLocation(dto.getLocation());
        existing.setInputDate(dto.getInputDate());
        existing.setRemark(dto.getRemark());

        MaterialInputEntity updated = materialInputRepository.save(existing);
        return MaterialInputDto.fromEntity(updated);
    }

    // ================= 삭제 =================
    @Transactional
    public void delete(Integer inputId) {
        MaterialInputEntity existing = materialInputRepository.findById(inputId)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 투입 내역 없음. ID=" + inputId));

        // ERP API 호출 - 재고 복구
        try {
            StockRequestDto req = new StockRequestDto(
                    existing.getMaterialId(),   // 이제 단순 ID
                    existing.getWarehouse(),
                    existing.getLocation(),
                    existing.getQuantity()
            );
            restTemplate.postForEntity(ERP_BASE_URL + "/api/inventory/restore", req, Void.class);

            materialInputRepository.deleteById(inputId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("ERP 재고 복구 실패. 삭제 중단", e);
        }

    }
}
