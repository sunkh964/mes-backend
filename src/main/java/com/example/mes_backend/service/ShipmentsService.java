package com.example.mes_backend.service;

import com.example.mes_backend.dto.ShipmentsDto;
import com.example.mes_backend.repository.ShipmentsRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShipmentsService {
    private final ShipmentsRepository shipmentsRepository;

    //전체 조회
    public List<ShipmentsDto> getAll(){
        return shipmentsRepository.findAll()
                .stream()
                .map(ShipmentsDto::fromEntity)
                .toList();
    }

    // 유틸: LIKE 패턴(%, _ 등 이스케이프) + 소문자 통일
    private String likePattern(String s) {
        if (s == null) return null;
        String escaped = s.trim()
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
        return "%" + escaped.toLowerCase() + "%"; // contains 검색: 앞뒤 %
    }

    // ---- 조건 검색 ----
    public List<ShipmentsDto> search(String salesOrderId,
                                     String customerId,
                                     String vesselId,
                                     LocalDateTime plannedShipDateFrom,
                                     LocalDateTime plannedShipDateTo,
                                     Integer status) {

        return shipmentsRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 수주번호 - 부분검색
            if (salesOrderId != null && !salesOrderId.isBlank()) {
                String pattern = likePattern(salesOrderId);
                predicates.add(
                        cb.like(cb.lower(root.get("salesOrder").get("salesOrderId").as(String.class)),
                                pattern, '\\') // escape char
                );
            }

            // 고객번호 - 부분검색
            if (customerId != null && !customerId.isBlank()) {
                String pattern = likePattern(customerId);
                predicates.add(
                        cb.like(cb.lower(root.get("customer").get("customerId").as(String.class)),
                                pattern, '\\')
                );
            }

            // 배번호 - 부분검색
            if (vesselId != null && !vesselId.isBlank()) {
                String pattern = likePattern(vesselId);
                predicates.add(
                        cb.like(cb.lower(root.get("vessel").get("vesselId").as(String.class)),
                                pattern, '\\')
                );
            }

            // 출하예정일 from~to (그대로)
            if (plannedShipDateFrom != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("plannedShipDate"), plannedShipDateFrom));
            }
            if (plannedShipDateTo != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("plannedShipDate"), plannedShipDateTo));
            }

            // 상태 (정확히 일치)
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }).stream().map(ShipmentsDto::fromEntity).toList();
    }


}
