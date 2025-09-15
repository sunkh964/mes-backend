package com.example.mes_backend.repository;

import com.example.mes_backend.entity.WorkCenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkCenterRepository extends JpaRepository<WorkCenterEntity, String> {

    // --- 기본 검색 조건 ---
    // 작업장아이디
    List<WorkCenterEntity> findByWorkCenterIdContaining(String workCenterId);

    // 작업장명
    List<WorkCenterEntity> findByWorkCenterNmContaining(String workCenterNm);

    // 활성 여부
    List<WorkCenterEntity> findByIsActive(boolean isActive);

    // 공정 아이디
    List<WorkCenterEntity> findByProcess_ProcessIdContaining(String processId);

    // --- 2가지 조건 조합 ---
    // 작업장아이디와 작업장명이 포함된 데이터 검색
    List<WorkCenterEntity> findByWorkCenterIdContainingAndWorkCenterNmContaining(String workCenterId, String workCenterNm);

    // 작업장아이디가 포함되고 활성 여부가 일치하는 데이터 검색
    List<WorkCenterEntity> findByWorkCenterIdContainingAndIsActive(String workCenterId, boolean isActive);

    // 작업장명이 포함되고 활성 여부가 일치하는 데이터 검색
    List<WorkCenterEntity> findByWorkCenterNmContainingAndIsActive(String workCenterNm, boolean isActive);

    // --- processId가 포함된 2가지 조건 조합
    List<WorkCenterEntity> findByWorkCenterIdContainingAndProcess_ProcessIdContaining(String workCenterId, String processId);

    List<WorkCenterEntity> findByWorkCenterNmContainingAndProcess_ProcessIdContaining(String workCenterNm, String processId);

    List<WorkCenterEntity> findByIsActiveAndProcess_ProcessIdContaining(boolean isActive, String processId);

    // --- 3가지 조건 조합 ---
    List<WorkCenterEntity> findByWorkCenterIdContainingAndWorkCenterNmContainingAndIsActive(String workCenterId, String workCenterNm, boolean isActive);

    // --- processId가 포함된 3가지 조건 조합 (추가된 부분) ---
    List<WorkCenterEntity> findByWorkCenterIdContainingAndWorkCenterNmContainingAndProcess_ProcessIdContaining(String workCenterId, String workCenterNm, String processId);

    List<WorkCenterEntity> findByWorkCenterIdContainingAndIsActiveAndProcess_ProcessIdContaining(String workCenterId, boolean isActive, String processId);

    List<WorkCenterEntity> findByWorkCenterNmContainingAndIsActiveAndProcess_ProcessIdContaining(String workCenterNm, boolean isActive, String processId);

    // --- 4가지 조건 모두 조합 (추가된 부분) ---
    List<WorkCenterEntity> findByWorkCenterIdContainingAndWorkCenterNmContainingAndIsActiveAndProcess_ProcessIdContaining(String workCenterId, String workCenterNm, boolean isActive, String processId);
};