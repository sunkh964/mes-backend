package com.example.mes_backend.service;

import com.example.mes_backend.dto.QualityControlDto;
import com.example.mes_backend.dto.SimpleStockRequestDto;
import com.example.mes_backend.entity.QualityControlEntity;
import com.example.mes_backend.repository.EmployeeRepository;
import com.example.mes_backend.repository.PurchaseOrderRepository;
import com.example.mes_backend.repository.QualityControlRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    private final RestTemplate restTemplate;

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
        // 1. 삭제할 QC 엔티티를 먼저 조회합니다.
        QualityControlEntity entityToDelete = qualityControlRepository.findById(qcId)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 품질검사 내역이 없습니다: " + qcId));

        // 2. 해당 QC가 'PASS'였고 합격수량이 있었다면, ERP 재고 복구를 시도합니다.
        if ("PASS".equalsIgnoreCase(entityToDelete.getResult()) && entityToDelete.getPassQuantity() > 0) {

            // 3. ERP에 보낼 DTO를 생성합니다.
            SimpleStockRequestDto requestDto = new SimpleStockRequestDto();
            requestDto.setMaterialId(entityToDelete.getMaterialId());
            requestDto.setQuantity(entityToDelete.getPassQuantity()); // 복구할 수량은 합격수량

            try {
                // 4. ERP의 새 재고 복구 API를 호출합니다.
                String erpApiUrl = "http://localhost:8081/api/inventory/restore-by-material";
                restTemplate.postForEntity(erpApiUrl, requestDto, Void.class);
                log.info("ERP 재고 복구 요청 성공 (QC ID: {})", qcId);

            } catch (Exception e) {
                log.error("ERP 재고 복구 요청 실패. MES 삭제 작업을 중단합니다. (QC ID: {}): {}", qcId, e.getMessage());
                // ERP 통신에 실패하면 MES 데이터도 삭제하지 않고 예외를 발생시켜 롤백합니다.
                throw new IllegalStateException("ERP 재고 복구에 실패하여 삭제할 수 없습니다.", e);
            }
        }

        // 5. ERP 재고 복구가 필요 없거나, 성공적으로 완료된 경우에만 MES DB에서 최종 삭제합니다.
        qualityControlRepository.deleteById(qcId);
    }


    /** ERP 동기화를 위해, 완료된 품질검사 목록을 조회하는 메서드 */
    public List<QualityControlDto> getCompletedQcForErp() {
        // "PENDING"이 아닌 "PASS" 또는 "FAIL" 상태의 모든 QC 결과를 조회
        return qualityControlRepository.findByResultNot("PENDING").stream()
                .map(QualityControlDto::fromEntity)
                .toList();
    }

}
