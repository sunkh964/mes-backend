package com.example.mes_backend.service;


import com.example.mes_backend.dto.ProcessDto;
import com.example.mes_backend.dto.WorkCenterDto;
import com.example.mes_backend.repository.WorkCenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkCenterService {

    private final WorkCenterRepository workCenterRepository;

    // 조건이 없을 경우 = 전체 조회
    public List<WorkCenterDto> getAllSearch(){
        return workCenterRepository.findAll()
                .stream()
                .map(WorkCenterDto::fromEntity)
                .toList();
    }

    // --- 1가지 조건 검색 ---
    public List<WorkCenterDto> findByWorkCenterIdContaining(String workCenterId) {
        return workCenterRepository.findByWorkCenterIdContaining(workCenterId).stream()
                .map(WorkCenterDto::fromEntity).toList();
    }

    public List<WorkCenterDto> findByWorkCenterNmContaining(String workCenterNm) {
        return workCenterRepository.findByWorkCenterNmContaining(workCenterNm).stream()
                .map(WorkCenterDto::fromEntity).toList();
    }

    public List<WorkCenterDto> findByIsActive(boolean isActive) {
        return workCenterRepository.findByIsActive(isActive).stream()
                .map(WorkCenterDto::fromEntity).toList();
    }

    public List<WorkCenterDto> findByProcessId(String processId) {
        return workCenterRepository.findByProcess_ProcessIdContaining(processId).stream()
                .map(WorkCenterDto::fromEntity).toList();
    }

    // --- 2가지 조건 검색 ---
    public List<WorkCenterDto> findByWorkCenterIdContainingAndWorkCenterNmContaining(String workCenterId, String workCenterNm) {
        return workCenterRepository.findByWorkCenterIdContainingAndWorkCenterNmContaining(workCenterId, workCenterNm).stream()
                .map(WorkCenterDto::fromEntity).toList();
    }

    public List<WorkCenterDto> findByWorkCenterIdContainingAndIsActive(String workCenterId, boolean isActive) {
        return workCenterRepository.findByWorkCenterIdContainingAndIsActive(workCenterId, isActive).stream()
                .map(WorkCenterDto::fromEntity).toList();
    }

    public List<WorkCenterDto> findByWorkCenterNmContainingAndIsActive(String workCenterNm, boolean isActive) {
        return workCenterRepository.findByWorkCenterNmContainingAndIsActive(workCenterNm, isActive).stream()
                .map(WorkCenterDto::fromEntity).toList();
    }

    public List<WorkCenterDto> findByWorkCenterIdContainingAndProcessId(String workCenterId, String processId) {
        return workCenterRepository.findByWorkCenterIdContainingAndProcess_ProcessIdContaining(workCenterId, processId).stream()
                .map(WorkCenterDto::fromEntity).toList();
    }

    public List<WorkCenterDto> findByWorkCenterNmContainingAndProcessId(String workCenterNm, String processId) {
        return workCenterRepository.findByWorkCenterNmContainingAndProcess_ProcessIdContaining(workCenterNm, processId).stream()
                .map(WorkCenterDto::fromEntity).toList();
    }

    public List<WorkCenterDto> findByIsActiveAndProcessId(boolean isActive, String processId) {
        return workCenterRepository.findByIsActiveAndProcess_ProcessIdContaining(isActive, processId).stream()
                .map(WorkCenterDto::fromEntity).toList();
    }

    // --- 3가지 조건 검색 ---
    public List<WorkCenterDto> findByWorkCenterIdContainingAndWorkCenterNmContainingAndIsActive(String workCenterId, String workCenterNm, boolean isActive) {
        return workCenterRepository.findByWorkCenterIdContainingAndWorkCenterNmContainingAndIsActive(workCenterId, workCenterNm, isActive).stream()
                .map(WorkCenterDto::fromEntity).toList();
    }

    public List<WorkCenterDto> findByWorkCenterIdContainingAndWorkCenterNmContainingAndProcessId(String workCenterId, String workCenterNm, String processId) {
        return workCenterRepository.findByWorkCenterIdContainingAndWorkCenterNmContainingAndProcess_ProcessIdContaining(workCenterId, workCenterNm, processId).stream()
                .map(WorkCenterDto::fromEntity).toList();
    }

    public List<WorkCenterDto> findByWorkCenterIdContainingAndIsActiveAndProcessId(String workCenterId, boolean isActive, String processId) {
        return workCenterRepository.findByWorkCenterIdContainingAndIsActiveAndProcess_ProcessIdContaining(workCenterId, isActive, processId).stream()
                .map(WorkCenterDto::fromEntity).toList();
    }

    public List<WorkCenterDto> findByWorkCenterNmContainingAndIsActiveAndProcessId(String workCenterNm, boolean isActive, String processId) {
        return workCenterRepository.findByWorkCenterNmContainingAndIsActiveAndProcess_ProcessIdContaining(workCenterNm, isActive, processId).stream()
                .map(WorkCenterDto::fromEntity).toList();
    }

    // --- 4가지 조건 검색 ---
    public List<WorkCenterDto> findByWorkCenterIdContainingAndWorkCenterNmContainingAndIsActiveAndProcessId(String workCenterId, String workCenterNm, boolean isActive, String processId) {
        return workCenterRepository.findByWorkCenterIdContainingAndWorkCenterNmContainingAndIsActiveAndProcess_ProcessIdContaining(workCenterId, workCenterNm, isActive, processId).stream()
                .map(WorkCenterDto::fromEntity).toList();
    }

    // 등록
    public WorkCenterDto create(WorkCenterDto dto) {
        if(workCenterRepository.existsById(dto.getWorkCenterId())){
            throw new IllegalArgumentException("이미 존재하는 작업장입니다." + dto.getWorkCenterId());
        }
        return WorkCenterDto.fromEntity(workCenterRepository.save(dto.toEntity()));
    }

    // ============= 삭제 =================
    public void delete(String workCenterId) {
        if (!workCenterRepository.existsById(workCenterId)) {
            throw new IllegalArgumentException("삭제할 작업장이 존재하지 않습니다: " + workCenterId);
        }
        workCenterRepository.deleteById(workCenterId);
    }

}
