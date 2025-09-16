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

    //조건 검색
    public List<ShipmentsDto> search(String salesOrderId,
                                     String customerId,
                                     String vesselId,
                                     LocalDateTime plannedShipDateFrom,
                                     LocalDateTime plannedShipDateTo,
                                     Integer status) {
        return shipmentsRepository.findAll((root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();

                    // 수주번호
                    if (salesOrderId != null && !salesOrderId.isEmpty()) {
                        predicates.add(cb.equal(root.get("salesOrder").get("salesOrderId"), salesOrderId));
                    }

                    // 고객번호
                    if (customerId != null && !customerId.isEmpty()) {
                        predicates.add(cb.equal(root.get("customer").get("customerId"), customerId));
                    }

                    // 배번호
                    if (vesselId != null && !vesselId.isEmpty()) {
                        predicates.add(cb.equal(root.get("vessel").get("vesselId"), vesselId));
                    }

                    // 출하예정일 (from ~ to)
                    if (plannedShipDateFrom != null) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("plannedShipDate"), plannedShipDateFrom));
                    }
                    if (plannedShipDateTo != null) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("plannedShipDate"), plannedShipDateTo));
                    }

                    // 상태
                    if (status != null) {
                        predicates.add(cb.equal(root.get("status"), status));
                    }

                    return cb.and(predicates.toArray(new Predicate[0]));
                }).stream()
                .map(ShipmentsDto::fromEntity)
                .toList();
    }

}
