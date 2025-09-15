package com.example.mes_backend.service;

import com.example.mes_backend.dto.ProcessDto;
import com.example.mes_backend.repository.ProcessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j // 로그를 남기기 위해 사용
@Service
@RequiredArgsConstructor // repository의 생성자를 자동으로 만들어주는 역할
public class ProcessService {

    private final ProcessRepository processRepository;

    // 1. 조건이 없을 경우 = 전체 조회
    public List<ProcessDto> getAllSearch(){
        return processRepository.findAll()
                .stream()
                .map(ProcessDto::fromEntity)
                .toList();
    }

    // 2. 공정 ID만 있을 경우
    public List<ProcessDto> getAllSearchProcessId(String processId) {
        return processRepository.findByProcessIdContaining(processId)
                .stream()
                .map(ProcessDto::fromEntity)
                .toList();
    }

    // 3. 공정명만 있을 경우
    public List<ProcessDto> getAllSearchProcessNm(String processNm) {
        return processRepository.findByProcessNmContaining(processNm)
                .stream()
                .map(ProcessDto::fromEntity)
                .toList();
    }

    // 4. 활성 여부만 있을 경우
    public List<ProcessDto> getAllSearchActive(boolean isActive) {
        return processRepository.findByIsActive(isActive)
                .stream()
                .map(ProcessDto::fromEntity)
                .toList();
    }

    // 5. 공정아이디와 공정명 조건이 있을 경우
    public List<ProcessDto> findByProcessIdProcessNm(String processId, String processNm){
        log.info("조회 조건 -> 공정아이디: {}, 공정명: {}", processId, processNm);
        return processRepository.findByProcessIdContainingAndProcessNmContaining(processId, processNm)
                .stream()
                .map(ProcessDto::fromEntity)
                .toList();
    }

    // 6. 공정명과 활성 여부 조건이 있을 경우
    public List<ProcessDto> getAllSearchProcessNmActive(String processNm, boolean isActive) {
        log.info("조회 조건 -> 공정명: {}, 활성여부: {}", processNm, isActive);
        return processRepository.findByProcessNmContainingAndIsActive(processNm, isActive)
                .stream()
                .map(ProcessDto::fromEntity)
                .toList();
    }

    // 7. 공정아이디와 활성 여부 조건이 있을 경우
    public List<ProcessDto> getAllSearchProcessIdActive(String processId, boolean isActive) {
        log.info("조회 조건 -> 공정아이디: {}, 활성여부: {}", processId, isActive);
        return processRepository.findByProcessIdContainingAndIsActive(processId, isActive)
                .stream()
                .map(ProcessDto::fromEntity)
                .toList();
    }

    // 8. 공정아이디, 공정명, 활성여부 모두 있을 경우
    public List<ProcessDto> getAllSearchProcessIdProcessNmActive(String processId, String processNm, boolean isActive) {
        log.info("조회 조건 -> 공정아이디: {}, 공정명 : {}, 활성여부: {}", processId, processNm, isActive);
        return processRepository.findByProcessIdContainingAndProcessNmContainingAndIsActive(processId, processNm, isActive)
                .stream()
                .map(ProcessDto::fromEntity)
                .toList();
    }


    // 9. 상세 조회 (ID 기준)
    public ProcessDto getProcess(String processId) {
        return processRepository.findById(processId)
                .map(ProcessDto::fromEntity)
                .orElse(null);
    }
}
