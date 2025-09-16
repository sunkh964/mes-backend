package com.example.mes_backend.repository;

import com.example.mes_backend.entity.MaterialInputEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MaterialInputRepository extends JpaRepository<MaterialInputEntity, Integer> {

    // --- 1가지 조건 ---
        // 작업지시서 ID로 자재 투입 목록 검색
        List<MaterialInputEntity> findByWorkOrder_WorkOrderId(Integer workOrderId);
        // 특정 자재의 투입 이력 검색
        List<MaterialInputEntity> findByMaterial_MaterialId(Integer materialId);
        // 특정 작업자를 포함하는 자재 투입 이력 검색
        List<MaterialInputEntity> findByEmployee_EmployeeIdContaining(String employeeId);
        // 특정 기간 사이에 투입된 자재 목록 검색
        List<MaterialInputEntity> findByInputDateBetween(LocalDateTime start, LocalDateTime end);

    // --- 2가지 조건 조합 ---
        // 작업지시서 ID와 자재 ID로 자재 투입 목록 검색
        List<MaterialInputEntity> findByWorkOrder_WorkOrderIdAndMaterial_MaterialId(Integer workOrderId, Integer materialId);
        // 작업지시서 ID와 작업자를 포함하는 자재 투입 목록 검색
        List<MaterialInputEntity> findByWorkOrder_WorkOrderIdAndEmployee_EmployeeIdContaining(Integer workOrderId, String employeeId);
        // 작업지시서 ID와 특정 기간으로 자재 투입 목록 검색
        List<MaterialInputEntity> findByWorkOrder_WorkOrderIdAndInputDateBetween(Integer workOrderId, LocalDateTime start, LocalDateTime end);
        // 특정 자재와 작업자를 포함하는 자재 투입 이력 검색
        List<MaterialInputEntity> findByMaterial_MaterialIdAndEmployee_EmployeeIdContaining(Integer materialId, String employeeId);
        // 특정 자재와 기간으로 자재 투입 이력 검색
        List<MaterialInputEntity> findByMaterial_MaterialIdAndInputDateBetween(Integer materialId, LocalDateTime start, LocalDateTime end);
        // 특정 작업자를 포함하고 특정 기간에 투입된 자재 이력 검색
        List<MaterialInputEntity> findByEmployee_EmployeeIdContainingAndInputDateBetween(String employeeId, LocalDateTime start, LocalDateTime end);

    // --- 3가지 조건 조합 ---
        // 작업지시서 ID, 자재 ID, 작업자를 포함하는 자재 투입 목록 검색
        List<MaterialInputEntity> findByWorkOrder_WorkOrderIdAndMaterial_MaterialIdAndEmployee_EmployeeIdContaining(Integer workOrderId, Integer materialId, String employeeId);
        // 작업지시서 ID, 자재 ID, 특정 기간으로 자재 투입 목록 검색
        List<MaterialInputEntity> findByWorkOrder_WorkOrderIdAndMaterial_MaterialIdAndInputDateBetween(Integer workOrderId, Integer materialId, LocalDateTime start, LocalDateTime end);
        // 작업지시서 ID, 작업자를 포함하고 특정 기간에 투입된 자재 목록 검색
        List<MaterialInputEntity> findByWorkOrder_WorkOrderIdAndEmployee_EmployeeIdContainingAndInputDateBetween(Integer workOrderId, String employeeId, LocalDateTime start, LocalDateTime end);
        // 특정 자재, 작업자를 포함하고 특정 기간에 투입된 자재 이력 검색
        List<MaterialInputEntity> findByMaterial_MaterialIdAndEmployee_EmployeeIdContainingAndInputDateBetween(Integer materialId, String employeeId, LocalDateTime start, LocalDateTime end);

    // --- 4가지 조건 조합 ---
        // 작업지시서 ID, 자재 ID, 작업자를 포함하고 특정 기간에 투입된 자재 목록 검색
        List<MaterialInputEntity> findByWorkOrder_WorkOrderIdAndMaterial_MaterialIdAndEmployee_EmployeeIdContainingAndInputDateBetween(Integer workOrderId, Integer materialId, String employeeId, LocalDateTime start, LocalDateTime end);
}
