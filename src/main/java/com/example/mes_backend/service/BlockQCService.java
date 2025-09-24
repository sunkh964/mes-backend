package com.example.mes_backend.service;

import com.example.mes_backend.dto.BlockQCDto;
import com.example.mes_backend.entity.BlockEntity;
import com.example.mes_backend.entity.BlockQCEntity;
import com.example.mes_backend.entity.Employee;
import com.example.mes_backend.entity.WorkOrderEntity;
import com.example.mes_backend.repository.BlockQCRepository;
import com.example.mes_backend.repository.BlockRepository;
import com.example.mes_backend.repository.EmployeeRepository;
import com.example.mes_backend.repository.WorkOrderRepository;
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
public class BlockQCService {

    private final BlockQCRepository blockQCRepository;
    private final WorkOrderRepository workOrderRepository;
    private final BlockRepository blockRepository;
    private final EmployeeRepository employeeRepository;

    // 전체 조회
    public List<BlockQCDto> getAll() {
        return blockQCRepository.findAll()
                .stream()
                .map(BlockQCDto::fromEntity)
                .toList();
    }

    // 조건 검색 (블록 ID, 검사 결과, 검사일자 범위)
    @Transactional
    public List<BlockQCDto> search(Integer blockId,
                                   String result,
                                   LocalDateTime from,
                                   LocalDateTime to) {

        return blockQCRepository.findAll((root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();

                    // 블록 ID 조건
                    if (blockId != null) {
                        predicates.add(cb.equal(root.get("block").get("blockId"), blockId));
                    }

                    // 검사 결과 조건 (대소문자 무시)
                    if (result != null && !result.isEmpty()) {
                        predicates.add(cb.equal(cb.upper(root.get("result")), result.toUpperCase()));
                    }

                    // 검사일자 시작 조건
                    if (from != null) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("inspectionDate"), from));
                    }

                    // 검사일자 종료 조건
                    if (to != null) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("inspectionDate"), to));
                    }

                    return cb.and(predicates.toArray(new Predicate[0]));
                })
                .stream()
                .map(BlockQCDto::fromEntity)
                .toList();
    }

    // ================= 신규 등록 =================
    @Transactional
    public BlockQCDto create(BlockQCDto dto) {
        BlockQCEntity entity = dto.toEntity();

        // 연관관계 주입
        WorkOrderEntity workOrder = workOrderRepository.findById(dto.getWorkOrderId())
                .orElseThrow(() -> new IllegalArgumentException("작업지시가 존재하지 않습니다: " + dto.getWorkOrderId()));
        BlockEntity block = blockRepository.findById(dto.getBlockId())
                .orElseThrow(() -> new IllegalArgumentException("블록이 존재하지 않습니다: " + dto.getBlockId()));
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("직원이 존재하지 않습니다: " + dto.getEmployeeId()));

        entity.setWorkOrder(workOrder);
        entity.setBlock(block);
        entity.setEmployee(employee);

        BlockQCEntity saved = blockQCRepository.save(entity);
        return BlockQCDto.fromEntity(saved);
    }

    // ================= 수정 =================
    @Transactional
    public BlockQCDto update(Integer blockQCId, BlockQCDto dto) {
        return blockQCRepository.findById(blockQCId)
                .map(entity -> {
                    // 연관 엔티티 조회
                    WorkOrderEntity workOrder = workOrderRepository.findById(dto.getWorkOrderId())
                            .orElseThrow(() -> new IllegalArgumentException("작업지시가 존재하지 않습니다: " + dto.getWorkOrderId()));
                    BlockEntity block = blockRepository.findById(dto.getBlockId())
                            .orElseThrow(() -> new IllegalArgumentException("블록이 존재하지 않습니다: " + dto.getBlockId()));
                    Employee employee = employeeRepository.findById(dto.getEmployeeId())
                            .orElseThrow(() -> new IllegalArgumentException("직원이 존재하지 않습니다: " + dto.getEmployeeId()));

                    // 연관관계 갱신
                    entity.setWorkOrder(workOrder);
                    entity.setBlock(block);
                    entity.setEmployee(employee);

                    // 일반 필드 갱신
                    entity.setInspectionDate(dto.getInspectionDate());
                    entity.setResult(dto.getResult());
                    entity.setPassQuantity(dto.getPassQuantity());
                    entity.setFailQuantity(dto.getFailQuantity());
                    entity.setDefectType(dto.getDefectType());
                    entity.setRemark(dto.getRemark());
                    entity.setUpdatedAt(LocalDateTime.now());

                    return BlockQCDto.fromEntity(blockQCRepository.save(entity));
                })
                .orElseThrow(() -> new IllegalArgumentException("수정할 QC 데이터가 존재하지 않습니다: " + blockQCId));
    }

    // ================= 삭제 =================
    @Transactional
    public void delete(Integer blockQCId) {
        if (!blockQCRepository.existsById(blockQCId)) {
            throw new IllegalArgumentException("삭제할 QC 데이터가 존재하지 않습니다: " + blockQCId);
        }
        blockQCRepository.deleteById(blockQCId);
    }
}
