package com.example.mes_backend.service;

import com.example.mes_backend.dto.MaterialInputDto;
import com.example.mes_backend.entity.MaterialInputEntity;
import com.example.mes_backend.repository.MaterialInputRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialInputService {

    private final MaterialInputRepository materialInputRepository;
    // 전체 자재 투입 목록 검색
        public List<MaterialInputDto> findAll() {
            return materialInputRepository.findAll().stream().map(MaterialInputDto::fromEntity).toList();
        }
    // --- 1가지 조건 ---

        // 작업지시서 ID로 자재 투입 목록 검색
        public List<MaterialInputDto> findByWorkOrderId(Integer workOrderId) {
            return materialInputRepository.findByWorkOrder_WorkOrderId(workOrderId).stream().map(MaterialInputDto::fromEntity).toList();
        }
        // 특정 자재의 투입 이력 검색
        public List<MaterialInputDto> findByMaterialId(Integer materialId) {
            return materialInputRepository.findByMaterial_MaterialId(materialId).stream().map(MaterialInputDto::fromEntity).toList();
        }
        // 특정 작업자를 포함하는 자재 투입 이력 검색
        public List<MaterialInputDto> findByEmployeeIdContaining(String employeeId) {
            return materialInputRepository.findByEmployee_EmployeeIdContaining(employeeId).stream().map(MaterialInputDto::fromEntity).toList();
        }
        // 특정 기간 사이에 투입된 자재 목록 검색
        public List<MaterialInputDto> findByInputDateBetween(LocalDateTime start, LocalDateTime end) {
            return materialInputRepository.findByInputDateBetween(start, end).stream().map(MaterialInputDto::fromEntity).toList();
        }

    // --- 2가지 조건 조합 ---
        // 작업지시서 ID와 자재 ID로 자재 투입 목록 검색
        public List<MaterialInputDto> findByWorkOrderIdAndMaterialId(Integer workOrderId, Integer materialId) {
            return materialInputRepository.findByWorkOrder_WorkOrderIdAndMaterial_MaterialId(workOrderId, materialId).stream().map(MaterialInputDto::fromEntity).toList();
        }
        // 작업지시서 ID와 작업자를 포함하는 자재 투입 목록 검색
        public List<MaterialInputDto> findByWorkOrderIdAndEmployeeIdContaining(Integer workOrderId, String employeeId) {
            return materialInputRepository.findByWorkOrder_WorkOrderIdAndEmployee_EmployeeIdContaining(workOrderId, employeeId).stream().map(MaterialInputDto::fromEntity).toList();
        }
        // 작업지시서 ID와 특정 기간으로 자재 투입 목록 검색
        public List<MaterialInputDto> findByWorkOrderIdAndInputDateBetween(Integer workOrderId, LocalDateTime start, LocalDateTime end) {
            return materialInputRepository.findByWorkOrder_WorkOrderIdAndInputDateBetween(workOrderId, start, end).stream().map(MaterialInputDto::fromEntity).toList();
        }
        // 특정 자재와 작업자를 포함하는 자재 투입 이력 검색
        public List<MaterialInputDto> findByMaterialIdAndEmployeeIdContaining(Integer materialId, String employeeId) {
            return materialInputRepository.findByMaterial_MaterialIdAndEmployee_EmployeeIdContaining(materialId, employeeId).stream().map(MaterialInputDto::fromEntity).toList();
        }
        // 특정 자재와 기간으로 자재 투입 이력 검색
        public List<MaterialInputDto> findByMaterialIdAndInputDateBetween(Integer materialId, LocalDateTime start, LocalDateTime end) {
            return materialInputRepository.findByMaterial_MaterialIdAndInputDateBetween(materialId, start, end).stream().map(MaterialInputDto::fromEntity).toList();
        }
        // 특정 작업자를 포함하고 특정 기간에 투입된 자재 이력 검색
        public List<MaterialInputDto> findByEmployeeIdContainingAndInputDateBetween(String employeeId, LocalDateTime start, LocalDateTime end) {
            return materialInputRepository.findByEmployee_EmployeeIdContainingAndInputDateBetween(employeeId, start, end).stream().map(MaterialInputDto::fromEntity).toList();
        }

    // --- 3가지 조건 조합 ---
        // 작업지시서 ID, 자재 ID, 작업자를 포함하는 자재 투입 목록 검색
        public List<MaterialInputDto> findByWorkOrderIdAndMaterialIdAndEmployeeIdContaining(Integer workOrderId, Integer materialId, String employeeId) {
            return materialInputRepository.findByWorkOrder_WorkOrderIdAndMaterial_MaterialIdAndEmployee_EmployeeIdContaining(workOrderId, materialId, employeeId).stream().map(MaterialInputDto::fromEntity).toList();
        }
        // 작업지시서 ID, 자재 ID, 특정 기간으로 자재 투입 목록 검색
        public List<MaterialInputDto> findByWorkOrderIdAndMaterialIdAndInputDateBetween(Integer workOrderId, Integer materialId, LocalDateTime start, LocalDateTime end) {
            return materialInputRepository.findByWorkOrder_WorkOrderIdAndMaterial_MaterialIdAndInputDateBetween(workOrderId, materialId, start, end).stream().map(MaterialInputDto::fromEntity).toList();
        }
        // 작업지시서 ID, 작업자를 포함하고 특정 기간에 투입된 자재 목록 검색
        public List<MaterialInputDto> findByWorkOrderIdAndEmployeeIdContainingAndInputDateBetween(Integer workOrderId, String employeeId, LocalDateTime start, LocalDateTime end) {
            return materialInputRepository.findByWorkOrder_WorkOrderIdAndEmployee_EmployeeIdContainingAndInputDateBetween(workOrderId, employeeId, start, end).stream().map(MaterialInputDto::fromEntity).toList();
        }
        // 특정 자재, 작업자를 포함하고 특정 기간에 투입된 자재 이력 검색
        public List<MaterialInputDto> findByMaterialIdAndEmployeeIdContainingAndInputDateBetween(Integer materialId, String employeeId, LocalDateTime start, LocalDateTime end) {
            return materialInputRepository.findByMaterial_MaterialIdAndEmployee_EmployeeIdContainingAndInputDateBetween(materialId, employeeId, start, end).stream().map(MaterialInputDto::fromEntity).toList();
        }

    // --- 4가지 조건 조합 ---
        // 작업지시서 ID, 자재 ID, 작업자를 포함하고 특정 기간에 투입된 자재 목록 검색
        public List<MaterialInputDto> findByWorkOrderIdAndMaterialIdAndEmployeeIdContainingAndInputDateBetween(Integer workOrderId, Integer materialId, String employeeId, LocalDateTime start, LocalDateTime end) {
            return materialInputRepository.findByWorkOrder_WorkOrderIdAndMaterial_MaterialIdAndEmployee_EmployeeIdContainingAndInputDateBetween(workOrderId, materialId, employeeId, start, end).stream().map(MaterialInputDto::fromEntity).toList();
        }
}
