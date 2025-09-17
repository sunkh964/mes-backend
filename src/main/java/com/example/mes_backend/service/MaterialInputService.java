package com.example.mes_backend.service;

import com.example.mes_backend.dto.MaterialInputDto;
import com.example.mes_backend.repository.MaterialInputRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialInputService {

    private final MaterialInputRepository materialInputRepository;

    /** ===================== [전체 조회] ===================== */
    // 모든 자재 사용 내역 조회
    public List<MaterialInputDto> findAll() {
        return materialInputRepository.findAll().stream()
                .map(MaterialInputDto::fromEntity)
                .toList();
    }

    /** ===================== [단일 조건 조회] ===================== */
    // 작업내역 ID로 조회
    public List<MaterialInputDto> findByResultId(Integer resultId) {
        return materialInputRepository.findByWorkResult_ResultId(resultId).stream()
                .map(MaterialInputDto::fromEntity)
                .toList();
    }

    // 작업지시 ID로 조회
    public List<MaterialInputDto> findByWorkOrderId(Integer workOrderId) {
        return materialInputRepository.findByWorkOrder_WorkOrderId(workOrderId).stream()
                .map(MaterialInputDto::fromEntity)
                .toList();
    }

    // 자재 ID로 조회
    public List<MaterialInputDto> findByMaterialId(Integer materialId) {
        return materialInputRepository.findByMaterial_MaterialId(materialId).stream()
                .map(MaterialInputDto::fromEntity)
                .toList();
    }

    // 창고명으로 조회
    public List<MaterialInputDto> findByWarehouse(String warehouse) {
        return materialInputRepository.findByWarehouse(warehouse).stream()
                .map(MaterialInputDto::fromEntity)
                .toList();
    }

    // 사용일자 범위 조회
    public List<MaterialInputDto> findByInputDateBetween(LocalDateTime start, LocalDateTime end) {
        return materialInputRepository.findByInputDateBetween(start, end).stream()
                .map(MaterialInputDto::fromEntity)
                .toList();
    }

    /** ===================== [두 가지 조건 조합] ===================== */
    // 작업지시 ID + 자재 ID
    public List<MaterialInputDto> findByWorkOrderIdAndMaterialId(Integer workOrderId, Integer materialId) {
        return materialInputRepository.findByWorkOrder_WorkOrderIdAndMaterial_MaterialId(workOrderId, materialId).stream()
                .map(MaterialInputDto::fromEntity)
                .toList();
    }

    // 작업지시 ID + 창고
    public List<MaterialInputDto> findByWorkOrderIdAndWarehouse(Integer workOrderId, String warehouse) {
        return materialInputRepository.findByWorkOrder_WorkOrderIdAndWarehouse(workOrderId, warehouse).stream()
                .map(MaterialInputDto::fromEntity)
                .toList();
    }

    // 작업지시 ID + 사용일자 범위
    public List<MaterialInputDto> findByWorkOrderIdAndInputDateBetween(Integer workOrderId, LocalDateTime start, LocalDateTime end) {
        return materialInputRepository.findByWorkOrder_WorkOrderIdAndInputDateBetween(workOrderId, start, end).stream()
                .map(MaterialInputDto::fromEntity)
                .toList();
    }

    // 자재 ID + 창고
    public List<MaterialInputDto> findByMaterialIdAndWarehouse(Integer materialId, String warehouse) {
        return materialInputRepository.findByMaterial_MaterialIdAndWarehouse(materialId, warehouse).stream()
                .map(MaterialInputDto::fromEntity)
                .toList();
    }

    // 자재 ID + 사용일자 범위
    public List<MaterialInputDto> findByMaterialIdAndInputDateBetween(Integer materialId, LocalDateTime start, LocalDateTime end) {
        return materialInputRepository.findByMaterial_MaterialIdAndInputDateBetween(materialId, start, end).stream()
                .map(MaterialInputDto::fromEntity)
                .toList();
    }

    // 창고 + 사용일자 범위
    public List<MaterialInputDto> findByWarehouseAndInputDateBetween(String warehouse, LocalDateTime start, LocalDateTime end) {
        return materialInputRepository.findByWarehouseAndInputDateBetween(warehouse, start, end).stream()
                .map(MaterialInputDto::fromEntity)
                .toList();
    }

    /** ===================== [세 가지 조건 조합] ===================== */
    // 작업지시 ID + 자재 ID + 창고
    public List<MaterialInputDto> findByWorkOrderIdAndMaterialIdAndWarehouse(Integer workOrderId, Integer materialId, String warehouse) {
        return materialInputRepository.findByWorkOrder_WorkOrderIdAndMaterial_MaterialIdAndWarehouse(workOrderId, materialId, warehouse).stream()
                .map(MaterialInputDto::fromEntity)
                .toList();
    }

    // 작업지시 ID + 자재 ID + 사용일자 범위
    public List<MaterialInputDto> findByWorkOrderIdAndMaterialIdAndInputDateBetween(Integer workOrderId, Integer materialId, LocalDateTime start, LocalDateTime end) {
        return materialInputRepository.findByWorkOrder_WorkOrderIdAndMaterial_MaterialIdAndInputDateBetween(workOrderId, materialId, start, end).stream()
                .map(MaterialInputDto::fromEntity)
                .toList();
    }

    // 작업지시 ID + 창고 + 사용일자 범위
    public List<MaterialInputDto> findByWorkOrderIdAndWarehouseAndInputDateBetween(Integer workOrderId, String warehouse, LocalDateTime start, LocalDateTime end) {
        return materialInputRepository.findByWorkOrder_WorkOrderIdAndWarehouseAndInputDateBetween(workOrderId, warehouse, start, end).stream()
                .map(MaterialInputDto::fromEntity)
                .toList();
    }

    // 자재 ID + 창고 + 사용일자 범위
    public List<MaterialInputDto> findByMaterialIdAndWarehouseAndInputDateBetween(Integer materialId, String warehouse, LocalDateTime start, LocalDateTime end) {
        return materialInputRepository.findByMaterial_MaterialIdAndWarehouseAndInputDateBetween(materialId, warehouse, start, end).stream()
                .map(MaterialInputDto::fromEntity)
                .toList();
    }

    /** ===================== [네 가지 조건 조합] ===================== */
    // 작업지시 ID + 자재 ID + 창고 + 사용일자 범위
    public List<MaterialInputDto> findByWorkOrderIdAndMaterialIdAndWarehouseAndInputDateBetween(Integer workOrderId, Integer materialId, String warehouse, LocalDateTime start, LocalDateTime end) {
        return materialInputRepository.findByWorkOrder_WorkOrderIdAndMaterial_MaterialIdAndWarehouseAndInputDateBetween(workOrderId, materialId, warehouse, start, end).stream()
                .map(MaterialInputDto::fromEntity)
                .toList();
    }
}
