package com.example.mes_backend.service;

import com.example.mes_backend.dto.QualityControlDto;
import com.example.mes_backend.entity.QualityControlEntity;
import com.example.mes_backend.repository.EmployeeRepository;
import com.example.mes_backend.repository.PurchaseOrderRepository;
import com.example.mes_backend.repository.QualityControlRepository;
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
public class QualityControlService {

    private final QualityControlRepository qualityControlRepository;
    private final EmployeeRepository employeeRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    //erp에서 웹훅으로 들어온 검사 요청을 저장
//    @Transactional
//    public QualityControlDto saveInspection(QualityControlDto dto){
//        //이부분 설계에서 result 값을 default로 해야하는 거아닌가....
//        if(dto.getResult() == null){
//            dto.setResult("PENDING");
//        }
//        QualityControlEntity entity = dto.toEntity();
//        QualityControlEntity save = qualityControlRepository.save(entity);
//        return QualityControlDto.fromEntity(save);
//    }

    @Transactional
    public List<QualityControlDto> saveInspections(List<QualityControlDto> qualityControlDtos) {
        List<QualityControlEntity> entities = new ArrayList<>();

        for (QualityControlDto dto : qualityControlDtos) {
            // DTO → Entity 변환 (예: dto.toEntity() 메서드 있다고 가정)
            QualityControlEntity entity = dto.toEntity();

            // 결과값 없으면 기본값 "PENDING" 설정
            if (entity.getResult() == null) {
                entity.setResult("PENDING");
            }
            entities.add(entity);
        }

        // DB 저장
        List<QualityControlEntity> saved = qualityControlRepository.saveAll(entities);

        // Entity → DTO 변환 후 리턴
        return saved.stream()
                .map(QualityControlDto::fromEntity)
                .toList();
    }


    // 전체 조회
    public List<QualityControlDto> getAll() {
        return qualityControlRepository.findAll()
                .stream()
                .map(QualityControlDto::fromEntity)
                .toList();
    }

    // 단건 조회
    public QualityControlDto getById(Integer qualityControlId) {
        return qualityControlRepository.findById(qualityControlId)
                .map(QualityControlDto::fromEntity)
                .orElse(null);

    }

    public List<QualityControlDto> search(String purchaseOrderId,
                                          Integer materialId,
                                          LocalDateTime inspectionDateFrom,
                                          LocalDateTime inspectionDateTo,
                                          String result) {

        return qualityControlRepository.findAll((root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();

                    // 발주 ID
                    if (purchaseOrderId != null && !purchaseOrderId.isEmpty()) {
                        predicates.add(cb.equal(root.get("purchaseOrderId"), purchaseOrderId));
                    }

                    // 자재 ID
                    if (materialId != null) {
                        predicates.add(cb.equal(root.get("materialId"), materialId));
                    }

                    // 검사일시 From
                    if (inspectionDateFrom != null) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("inspectionDate"), inspectionDateFrom));
                    }

                    // 검사일시 To
                    if (inspectionDateTo != null) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("inspectionDate"), inspectionDateTo));
                    }

                    // 검사 결과
                    if (result != null && !result.isEmpty()) {
                        predicates.add(cb.equal(root.get("result"), result));
                    }

                    return cb.and(predicates.toArray(new Predicate[0]));
                }).stream()
                .map(QualityControlDto::fromEntity)
                .toList();
    }

    // 신규 등록
    @Transactional
    public QualityControlDto create(QualityControlDto dto) {
        QualityControlEntity entity = dto.toEntity();

        // 여기서 DB에 있는 실제 엔티티를 가져옴
        if (dto.getInspectorId() != null) {
            entity.setInspectorId(employeeRepository.getReferenceById(dto.getInspectorId()));
        }
        if (dto.getPurchaseOrderId() != null) {
            entity.setPurchaseOrderId(purchaseOrderRepository.getReferenceById(dto.getPurchaseOrderId()));
        }

        return QualityControlDto.fromEntity(qualityControlRepository.save(entity));
    }

    // 수정
    @Transactional
    public QualityControlDto update(Integer qcId, QualityControlDto dto) {
        return qualityControlRepository.findById(qcId)
                .map(existing -> {
                    QualityControlEntity updated = dto.toEntity();
                    updated.setQcId(existing.getQcId());
                    if (updated.getResult() == null) {
                        updated.setResult("PENDING");
                    }
                    return QualityControlDto.fromEntity(qualityControlRepository.save(updated));
                })
                .orElseThrow(() -> new IllegalArgumentException("품질검사 내역이 존재하지 않습니다: " + qcId));
    }

    // 삭제
    @Transactional
    public void delete(Integer qcId) {
        if (!qualityControlRepository.existsById(qcId)) {
            throw new IllegalArgumentException("삭제할 품질검사 내역이 존재하지 않습니다: " + qcId);
        }
        qualityControlRepository.deleteById(qcId);
    }

}
