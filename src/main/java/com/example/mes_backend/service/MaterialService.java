package com.example.mes_backend.service;

import com.example.mes_backend.dto.MaterialDto;
import com.example.mes_backend.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;

    // 전체 조회
    public List<MaterialDto> findAll() {
        return materialRepository.findAll().stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }

    // --- 1가지 조건 ---
    // 자재명을 포함하는 데이터 검색
    public List<MaterialDto> findByMaterialNmContaining(String materialNm) {
        return materialRepository.findByMaterialNmContaining(materialNm).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    // 카테고리를 포함하는 데이터 검색
    public List<MaterialDto> findByCategoryContaining(String category) {
        return materialRepository.findByCategoryContaining(category).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    // 상태가 일치하는 데이터 검색
    public List<MaterialDto> findByStatus(Integer status) {
        return materialRepository.findByStatus(status).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    // 창고명을 포함하는 데이터 검색
    public List<MaterialDto> findByWarehouseContaining(String warehouse) {
        return materialRepository.findByWarehouseContaining(warehouse).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    // 공급처 ID가 일치하는 데이터 검색
    public List<MaterialDto> findBySupplier_SupplierId(Long supplierId) {
        return materialRepository.findBySupplier_SupplierId(supplierId).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }

    // --- 2가지 조건 조합 ---
    // 자재명(포함)과 카테고리(포함)로 데이터 검색
    public List<MaterialDto> findByMaterialNmContainingAndCategoryContaining(String m, String c) {
        return materialRepository.findByMaterialNmContainingAndCategoryContaining(m, c).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    // 자재명(포함)과 상태(일치)로 데이터 검색
    public List<MaterialDto> findByMaterialNmContainingAndStatus(String m, Integer s) {
        return materialRepository.findByMaterialNmContainingAndStatus(m, s).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    // 자재명(포함)과 창고명(포함)으로 데이터 검색
    public List<MaterialDto> findByMaterialNmContainingAndWarehouseContaining(String m, String w) {
        return materialRepository.findByMaterialNmContainingAndWarehouseContaining(m, w).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    // 자재명(포함)과 공급처 ID(일치)로 데이터 검색
    public List<MaterialDto> findByMaterialNmContainingAndSupplier_SupplierId(String m, Long s) {
        return materialRepository.findByMaterialNmContainingAndSupplier_SupplierId(m, s).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    // 카테고리(포함)와 상태(일치)로 데이터 검색
    public List<MaterialDto> findByCategoryContainingAndStatus(String c, Integer s) {
        return materialRepository.findByCategoryContainingAndStatus(c, s).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    // 카테고리(포함)와 창고명(포함)으로 데이터 검색
    public List<MaterialDto> findByCategoryContainingAndWarehouseContaining(String c, String w) {
        return materialRepository.findByCategoryContainingAndWarehouseContaining(c, w).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    // 카테고리(포함)와 공급처 ID(일치)로 데이터 검색
    public List<MaterialDto> findByCategoryContainingAndSupplier_SupplierId(String c, Long s) {
        return materialRepository.findByCategoryContainingAndSupplier_SupplierId(c, s).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    // 상태(일치)와 창고명(포함)으로 데이터 검색
    public List<MaterialDto> findByStatusAndWarehouseContaining(Integer s, String w) {
        return materialRepository.findByStatusAndWarehouseContaining(s, w).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    // 상태(일치)와 공급처 ID(일치)로 데이터 검색
    public List<MaterialDto> findByStatusAndSupplier_SupplierId(Integer s, Long sup) {
        return materialRepository.findByStatusAndSupplier_SupplierId(s, sup).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    // 창고명(포함)과 공급처 ID(일치)로 데이터 검색
    public List<MaterialDto> findByWarehouseContainingAndSupplier_SupplierId(String w, Long s) {
        return materialRepository.findByWarehouseContainingAndSupplier_SupplierId(w, s).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }

    // --- 3가지 조건 조합 ---
    // 자재명(포함), 카테고리(포함), 상태(일치)로 데이터 검색
    public List<MaterialDto> findByMaterialNmContainingAndCategoryContainingAndStatus(String m, String c, Integer s) {
        return materialRepository.findByMaterialNmContainingAndCategoryContainingAndStatus(m, c, s).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    // ... (나머지 3, 4, 5가지 조건 조합 메소드들도 동일한 패턴으로 추가) ...
    public List<MaterialDto> findByMaterialNmContainingAndCategoryContainingAndWarehouseContaining(String m, String c, String w) {
        return materialRepository.findByMaterialNmContainingAndCategoryContainingAndWarehouseContaining(m, c, w).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    public List<MaterialDto> findByMaterialNmContainingAndCategoryContainingAndSupplier_SupplierId(String m, String c, Long s) {
        return materialRepository.findByMaterialNmContainingAndCategoryContainingAndSupplier_SupplierId(m, c, s).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    public List<MaterialDto> findByMaterialNmContainingAndStatusAndWarehouseContaining(String m, Integer s, String w) {
        return materialRepository.findByMaterialNmContainingAndStatusAndWarehouseContaining(m, s, w).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    public List<MaterialDto> findByMaterialNmContainingAndStatusAndSupplier_SupplierId(String m, Integer s, Long sup) {
        return materialRepository.findByMaterialNmContainingAndStatusAndSupplier_SupplierId(m, s, sup).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    public List<MaterialDto> findByMaterialNmContainingAndWarehouseContainingAndSupplier_SupplierId(String m, String w, Long s) {
        return materialRepository.findByMaterialNmContainingAndWarehouseContainingAndSupplier_SupplierId(m, w, s).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    public List<MaterialDto> findByCategoryContainingAndStatusAndWarehouseContaining(String c, Integer s, String w) {
        return materialRepository.findByCategoryContainingAndStatusAndWarehouseContaining(c, s, w).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    public List<MaterialDto> findByCategoryContainingAndStatusAndSupplier_SupplierId(String c, Integer s, Long sup) {
        return materialRepository.findByCategoryContainingAndStatusAndSupplier_SupplierId(c, s, sup).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    public List<MaterialDto> findByCategoryContainingAndWarehouseContainingAndSupplier_SupplierId(String c, String w, Long s) {
        return materialRepository.findByCategoryContainingAndWarehouseContainingAndSupplier_SupplierId(c, w, s).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    public List<MaterialDto> findByStatusAndWarehouseContainingAndSupplier_SupplierId(Integer s, String w, Long sup) {
        return materialRepository.findByStatusAndWarehouseContainingAndSupplier_SupplierId(s, w, sup).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }

    // --- 4가지 조건 조합 ---
    // 자재명(포함), 카테고리(포함), 상태(일치), 창고명(포함)으로 데이터 검색
    public List<MaterialDto> findByMaterialNmContainingAndCategoryContainingAndStatusAndWarehouseContaining(String m, String c, Integer s, String w) {
        return materialRepository.findByMaterialNmContainingAndCategoryContainingAndStatusAndWarehouseContaining(m, c, s, w).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    // 자재명(포함), 카테고리(포함), 상태(일치), 공급처 ID(일치)로 데이터 검색
    public List<MaterialDto> findByMaterialNmContainingAndCategoryContainingAndStatusAndSupplier_SupplierId(String m, String c, Integer s, Long sup) {
        return materialRepository.findByMaterialNmContainingAndCategoryContainingAndStatusAndSupplier_SupplierId(m, c, s, sup).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    // 자재명(포함), 카테고리(포함), 창고명(포함), 공급처 ID(일치)로 데이터 검색
    public List<MaterialDto> findByMaterialNmContainingAndCategoryContainingAndWarehouseContainingAndSupplier_SupplierId(String m, String c, String w, Long s) {
        return materialRepository.findByMaterialNmContainingAndCategoryContainingAndWarehouseContainingAndSupplier_SupplierId(m, c, w, s).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    // 자재명(포함), 상태(일치), 창고명(포함), 공급처 ID(일치)로 데이터 검색
    public List<MaterialDto> findByMaterialNmContainingAndStatusAndWarehouseContainingAndSupplier_SupplierId(String m, Integer s, String w, Long sup) {
        return materialRepository.findByMaterialNmContainingAndStatusAndWarehouseContainingAndSupplier_SupplierId(m, s, w, sup).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
    // 카테고리(포함), 상태(일치), 창고명(포함), 공급처 ID(일치)로 데이터 검색
    public List<MaterialDto> findByCategoryContainingAndStatusAndWarehouseContainingAndSupplier_SupplierId(String c, Integer s, String w, Long sup) {
        return materialRepository.findByCategoryContainingAndStatusAndWarehouseContainingAndSupplier_SupplierId(c, s, w, sup).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }

    // --- 5가지 조건 조합 ---
    // 자재명(포함), 카테고리(포함), 상태(일치), 창고명(포함), 공급처 ID(일치)로 데이터 검색
    public List<MaterialDto> findByMaterialNmContainingAndCategoryContainingAndStatusAndWarehouseContainingAndSupplier_SupplierId(String m, String c, Integer s, String w, Long sup) {
        return materialRepository.findByMaterialNmContainingAndCategoryContainingAndStatusAndWarehouseContainingAndSupplier_SupplierId(m, c, s, w, sup).stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }
}
