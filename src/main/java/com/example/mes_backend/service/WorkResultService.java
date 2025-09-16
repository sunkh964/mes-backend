package com.example.mes_backend.service;

import com.example.mes_backend.dto.WorkResultDto;
import com.example.mes_backend.repository.WorkResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class WorkResultService {

    private final WorkResultRepository workResultRepository;

    // 전체 조회 ---
    public List<WorkResultDto> findAll() {
        return workResultRepository.findAll().stream()
                .map(WorkResultDto::fromEntity)
                .toList();
    }

    // --- 1가지 조건 ---
        // 작업지시서 ID로 조회 (containing)
        public List<WorkResultDto> findByWorkOrderId(Integer workOrderId) {
            return workResultRepository.findByWorkOrder_WorkOrderId(workOrderId).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 작업자 ID로 조회 (containing)
        public List<WorkResultDto> findByEmployeeId(String employeeId) {
            return workResultRepository.findByEmployee_EmployeeIdContaining(employeeId).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 작업장 ID로 조회 (containing)
        public List<WorkResultDto> findByWorkCenterId(String workCenterId) {
            return workResultRepository.findByWorkCenter_WorkCenterIdContaining(workCenterId).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 설비 ID로 조회 (containing)
        public List<WorkResultDto> findByEquipmentId(String equipmentId) {
            return workResultRepository.findByEquipment_EquipmentIdContaining(equipmentId).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 현재 상태로 조회
        public List<WorkResultDto> findByStatus(String status) {
            return workResultRepository.findByStatus(status).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 시간으로 조회
        public List<WorkResultDto> findByStartTimeBetween(LocalDateTime start, LocalDateTime end) {
            return workResultRepository.findByStartTimeBetween(start, end).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }

    // --- 2가지 조건 조합 ---
        // 작업지시서 ID와 작업자 ID로 조회
        public List<WorkResultDto> findByWorkOrderIdAndEmployeeId(Integer workOrderId, String employeeId) {
            return workResultRepository.findByWorkOrder_WorkOrderIdAndEmployee_EmployeeIdContaining(workOrderId, employeeId).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 작업지시서 ID와 작업장 ID로 조회
        public List<WorkResultDto> findByWorkOrderIdAndWorkCenterId(Integer workOrderId, String workCenterId) {
            return workResultRepository.findByWorkOrder_WorkOrderIdAndWorkCenter_WorkCenterIdContaining(workOrderId, workCenterId).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 작업지시서 ID와 설비 ID로 조회
        public List<WorkResultDto> findByWorkOrderIdAndEquipmentId(Integer workOrderId, String equipmentId) {
            return workResultRepository.findByWorkOrder_WorkOrderIdAndEquipment_EquipmentIdContaining(workOrderId, equipmentId).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 작업지시서 ID와 상태로 조회
        public List<WorkResultDto> findByWorkOrderIdAndStatus(Integer workOrderId, String status) {
            return workResultRepository.findByWorkOrder_WorkOrderIdAndStatus(workOrderId, status).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 작업지시서 ID와 기간으로 조회
        public List<WorkResultDto> findByWorkOrderIdAndStartTimeBetween(Integer workOrderId, LocalDateTime start, LocalDateTime end) {
            return workResultRepository.findByWorkOrder_WorkOrderIdAndStartTimeBetween(workOrderId, start, end).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 작업자 ID와 작업장 ID로 조회
        public List<WorkResultDto> findByEmployeeIdAndWorkCenterId(String employeeId, String workCenterId) {
            return workResultRepository.findByEmployee_EmployeeIdContainingAndWorkCenter_WorkCenterIdContaining(employeeId, workCenterId).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 작업자 ID와 설비 ID로 조회
        public List<WorkResultDto> findByEmployeeIdAndEquipmentId(String employeeId, String equipmentId) {
            return workResultRepository.findByEmployee_EmployeeIdContainingAndEquipment_EquipmentIdContaining(employeeId, equipmentId).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 작업자 ID와 상태로 조회
        public List<WorkResultDto> findByEmployeeIdAndStatus(String employeeId, String status) {
            return workResultRepository.findByEmployee_EmployeeIdContainingAndStatus(employeeId, status).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 작업자 ID와 기간으로 조회
        public List<WorkResultDto> findByEmployeeIdAndStartTimeBetween(String employeeId, LocalDateTime start, LocalDateTime end) {
            return workResultRepository.findByEmployee_EmployeeIdContainingAndStartTimeBetween(employeeId, start, end).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 작업장 ID와 설비 ID로 조회
        public List<WorkResultDto> findByWorkCenterIdAndEquipmentId(String workCenterId, String equipmentId) {
            return workResultRepository.findByWorkCenter_WorkCenterIdContainingAndEquipment_EquipmentIdContaining(workCenterId, equipmentId).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 작업장 ID와 상태로 조회
        public List<WorkResultDto> findByWorkCenterIdAndStatus(String workCenterId, String status) {
            return workResultRepository.findByWorkCenter_WorkCenterIdContainingAndStatus(workCenterId, status).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 작업장 ID와 기간으로 조회
        public List<WorkResultDto> findByWorkCenterIdAndStartTimeBetween(String workCenterId, LocalDateTime start, LocalDateTime end) {
            return workResultRepository.findByWorkCenter_WorkCenterIdContainingAndStartTimeBetween(workCenterId, start, end).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 설비 ID와 상태로 조회
        public List<WorkResultDto> findByEquipmentIdAndStatus(String equipmentId, String status) {
            return workResultRepository.findByEquipment_EquipmentIdContainingAndStatus(equipmentId, status).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 설비 ID와 기간으로 조회
        public List<WorkResultDto> findByEquipmentIdAndStartTimeBetween(String equipmentId, LocalDateTime start, LocalDateTime end) {
            return workResultRepository.findByEquipment_EquipmentIdContainingAndStartTimeBetween(equipmentId, start, end).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 상태와 기간으로 조회
        public List<WorkResultDto> findByStatusAndStartTimeBetween(String status, LocalDateTime start, LocalDateTime end) {
            return workResultRepository.findByStatusAndStartTimeBetween(status, start, end).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }

    // --- 3가지 조건 조합 ---
        // 작업지시서 ID, 작업자 ID, 상태로 조회
        public List<WorkResultDto> findByWorkOrderIdAndEmployeeIdAndStatus(Integer workOrderId, String employeeId, String status) {
            return workResultRepository.findByWorkOrder_WorkOrderIdAndEmployee_EmployeeIdContainingAndStatus(workOrderId, employeeId, status).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 작업지시서 ID, 작업장 ID, 상태로 조회
        public List<WorkResultDto> findByWorkOrderIdAndWorkCenterIdAndStatus(Integer workOrderId, String workCenterId, String status) {
            return workResultRepository.findByWorkOrder_WorkOrderIdAndWorkCenter_WorkCenterIdContainingAndStatus(workOrderId, workCenterId, status).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 작업장 ID, 설비 ID, 상태로 조회
        public List<WorkResultDto> findByWorkCenterIdAndEquipmentIdAndStatus(String workCenterId, String equipmentId, String status) {
            return workResultRepository.findByWorkCenter_WorkCenterIdContainingAndEquipment_EquipmentIdContainingAndStatus(workCenterId, equipmentId, status).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 작업자 ID, 상태, 기간으로 조회
        public List<WorkResultDto> findByEmployeeIdAndStatusAndStartTimeBetween(String employeeId, String status, LocalDateTime start, LocalDateTime end) {
            return workResultRepository.findByEmployee_EmployeeIdContainingAndStatusAndStartTimeBetween(employeeId, status, start, end).stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
    // --- 4가지 조건 조합 ---
        // 작업지시서 ID, 작업장 ID, 설비 ID, 상태로 조회
        public List<WorkResultDto> findByWorkOrderIdAndWorkCenterIdAndEquipmentIdAndStatus(Integer workOrderId, String workCenterId, String equipmentId, String status) {
            return workResultRepository.findByWorkOrder_WorkOrderIdAndWorkCenter_WorkCenterIdContainingAndEquipment_EquipmentIdContainingAndStatus(workOrderId, workCenterId, equipmentId, status)
                    .stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
        // 작업지시서 ID, 작업자 ID, 상태, 기간으로 조회
        public List<WorkResultDto> findByWorkOrderIdAndEmployeeIdAndStatusAndStartTimeBetween(Integer workOrderId, String employeeId, String status, LocalDateTime start, LocalDateTime end) {
            return workResultRepository.findByWorkOrder_WorkOrderIdAndEmployee_EmployeeIdContainingAndStatusAndStartTimeBetween(workOrderId, employeeId, status, start, end)
                    .stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
    // --- 5가지 조건 조합 ---
        // 작업지시서 ID, 작업자 ID, 작업장 ID, 설비 ID, 상태로 조회
        public List<WorkResultDto> findByWorkOrderIdAndEmployeeIdAndWorkCenterIdAndEquipmentIdAndStatus(Integer workOrderId, String employeeId, String workCenterId, String equipmentId, String status) {
            return workResultRepository.findByWorkOrder_WorkOrderIdAndEmployee_EmployeeIdContainingAndWorkCenter_WorkCenterIdContainingAndEquipment_EquipmentIdContainingAndStatus(workOrderId, employeeId, workCenterId, equipmentId, status)
                    .stream()
                    .map(WorkResultDto::fromEntity)
                    .toList();
        }
}
