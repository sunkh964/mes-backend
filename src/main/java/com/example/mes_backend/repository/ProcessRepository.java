package com.example.mes_backend.repository;

import com.example.mes_backend.entity.ProcessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessRepository extends JpaRepository<ProcessEntity, String> {  // 공정아이디가 일치하는 데이터 검색 기능이 포함

    // 공정아이디(processId)와 일치하는 데이터 검색
    List<ProcessEntity> findByProcessId(String processId);

    // 공정명(processNm)을 포함하는(Containing) 데이터 검색
    List<ProcessEntity> findByProcessNmContaining(String processNm);

    // 활성 여부(isActive)가 일치하는 데이터 검색
    List<ProcessEntity> findByIsActive(boolean isActive);

    // 공정아이디가 일치하고 공정명을 포함하는 데이터 검색
    List<ProcessEntity> findByProcessIdContainingAndProcessNmContaining(String processId, String processNm);

    // 공정명을 포함하고 활성 여부가 일치하는 데이터 검색
    List<ProcessEntity> findByProcessNmContainingAndIsActive(String processNm, boolean isActive);

    // 공정아이디가 일치하고 활성 여부가 일치하는 데이터 검색
    List<ProcessEntity> findByProcessIdContainingAndIsActive(String processId, boolean isActive);

    // 공정아이디가 일치하고 공정명을 포함하고 활성 여부가 일치하는 데이터 검색
    List<ProcessEntity> findByProcessIdContainingAndProcessNmContainingAndIsActive(String processId, String processNm, boolean isActive);
}
