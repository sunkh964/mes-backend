package com.example.mes_backend.repository;

import com.example.mes_backend.entity.WorkResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkResultRepository extends JpaRepository<WorkResultEntity, Integer> {

    // --- 1가지 조건 ---
    List<WorkResultEntity> findByWorkOrder_WorkOrderId(Integer workOrderId);
    List<WorkResultEntity> findByEmployee_EmployeeIdContaining(String employeeId);
    List<WorkResultEntity> findByWorkCenter_WorkCenterIdContaining(String workCenterId);
    List<WorkResultEntity> findByEquipment_EquipmentIdContaining(String equipmentId);
    List<WorkResultEntity> findByStatus(String status);
    List<WorkResultEntity> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    // --- 2가지 조건 조합 ---
    List<WorkResultEntity> findByWorkOrder_WorkOrderIdAndEmployee_EmployeeIdContaining(Integer workOrderId, String employeeId);
    List<WorkResultEntity> findByWorkOrder_WorkOrderIdAndWorkCenter_WorkCenterIdContaining(Integer workOrderId, String workCenterId);
    List<WorkResultEntity> findByWorkOrder_WorkOrderIdAndEquipment_EquipmentIdContaining(Integer workOrderId, String equipmentId);
    List<WorkResultEntity> findByWorkOrder_WorkOrderIdAndStatus(Integer workOrderId, String status);
    List<WorkResultEntity> findByWorkOrder_WorkOrderIdAndStartTimeBetween(Integer workOrderId, LocalDateTime start, LocalDateTime end);
    List<WorkResultEntity> findByEmployee_EmployeeIdContainingAndWorkCenter_WorkCenterIdContaining(String employeeId, String workCenterId);
    List<WorkResultEntity> findByEmployee_EmployeeIdContainingAndEquipment_EquipmentIdContaining(String employeeId, String equipmentId);
    List<WorkResultEntity> findByEmployee_EmployeeIdContainingAndStatus(String employeeId, String status);
    List<WorkResultEntity> findByEmployee_EmployeeIdContainingAndStartTimeBetween(String employeeId, LocalDateTime start, LocalDateTime end);
    List<WorkResultEntity> findByWorkCenter_WorkCenterIdContainingAndEquipment_EquipmentIdContaining(String workCenterId, String equipmentId);
    List<WorkResultEntity> findByWorkCenter_WorkCenterIdContainingAndStatus(String workCenterId, String status);
    List<WorkResultEntity> findByWorkCenter_WorkCenterIdContainingAndStartTimeBetween(String workCenterId, LocalDateTime start, LocalDateTime end);
    List<WorkResultEntity> findByEquipment_EquipmentIdContainingAndStatus(String equipmentId, String status);
    List<WorkResultEntity> findByEquipment_EquipmentIdContainingAndStartTimeBetween(String equipmentId, LocalDateTime start, LocalDateTime end);
    List<WorkResultEntity> findByStatusAndStartTimeBetween(String status, LocalDateTime start, LocalDateTime end);

    // --- 3가지 조건 조합 ---
    List<WorkResultEntity> findByWorkOrder_WorkOrderIdAndEmployee_EmployeeIdContainingAndStatus(Integer workOrderId, String employeeId, String status);
    List<WorkResultEntity> findByWorkOrder_WorkOrderIdAndWorkCenter_WorkCenterIdContainingAndStatus(Integer workOrderId, String workCenterId, String status);
    List<WorkResultEntity> findByWorkCenter_WorkCenterIdContainingAndEquipment_EquipmentIdContainingAndStatus(String workCenterId, String equipmentId, String status);
    List<WorkResultEntity> findByEmployee_EmployeeIdContainingAndStatusAndStartTimeBetween(String employeeId, String status, LocalDateTime start, LocalDateTime end);

    // --- 4가지 조건 조합 ---
    List<WorkResultEntity> findByWorkOrder_WorkOrderIdAndWorkCenter_WorkCenterIdContainingAndEquipment_EquipmentIdContainingAndStatus(Integer workOrderId, String workCenterId, String equipmentId, String status);
    List<WorkResultEntity> findByWorkOrder_WorkOrderIdAndEmployee_EmployeeIdContainingAndStatusAndStartTimeBetween(Integer workOrderId, String employeeId, String status, LocalDateTime start, LocalDateTime end);

    // --- 5가지 조건 조합 ---
    List<WorkResultEntity> findByWorkOrder_WorkOrderIdAndEmployee_EmployeeIdContainingAndWorkCenter_WorkCenterIdContainingAndEquipment_EquipmentIdContainingAndStatus(Integer workOrderId, String employeeId, String workCenterId, String equipmentId, String status);
}
