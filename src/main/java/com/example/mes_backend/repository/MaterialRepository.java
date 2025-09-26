//package com.example.mes_backend.repository;
//
//import com.example.mes_backend.entity.MaterialEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface MaterialRepository extends JpaRepository<MaterialEntity, Integer> {
//
//
//    // --- 1가지 조건 (5개) ---
//        // 자재명을 포함하는 데이터 검색
//        List<MaterialEntity> findByMaterialNmContaining(String materialNm);
//        // 카테고리를 포함하는 데이터 검색
//        List<MaterialEntity> findByCategoryContaining(String category);
//        // 상태가 일치하는 데이터 검색
//        List<MaterialEntity> findByStatus(Integer status);
//        // 창고명을 포함하는 데이터 검색
//        List<MaterialEntity> findByWarehouseContaining(String warehouse);
//        // 공급처 ID가 일치하는 데이터 검색
//        List<MaterialEntity> findBySupplier_SupplierId(Long supplierId);
//
//    // --- 2가지 조건 조합 (10개) ---
//        // 자재명(포함)과 카테고리(포함)로 데이터 검색
//        List<MaterialEntity> findByMaterialNmContainingAndCategoryContaining(String materialNm, String category);
//        // 자재명(포함)과 상태(일치)로 데이터 검색
//        List<MaterialEntity> findByMaterialNmContainingAndStatus(String materialNm, Integer status);
//        // 자재명(포함)과 창고명(포함)으로 데이터 검색
//        List<MaterialEntity> findByMaterialNmContainingAndWarehouseContaining(String materialNm, String warehouse);
//        // 자재명(포함)과 공급처 ID(일치)로 데이터 검색
//        List<MaterialEntity> findByMaterialNmContainingAndSupplier_SupplierId(String materialNm, Long supplierId);
//        // 카테고리(포함)와 상태(일치)로 데이터 검색
//        List<MaterialEntity> findByCategoryContainingAndStatus(String category, Integer status);
//        // 카테고리(포함)와 창고명(포함)으로 데이터 검색
//        List<MaterialEntity> findByCategoryContainingAndWarehouseContaining(String category, String warehouse);
//        // 카테고리(포함)와 공급처 ID(일치)로 데이터 검색
//        List<MaterialEntity> findByCategoryContainingAndSupplier_SupplierId(String category, Long supplierId);
//        // 상태(일치)와 창고명(포함)으로 데이터 검색
//        List<MaterialEntity> findByStatusAndWarehouseContaining(Integer status, String warehouse);
//        // 상태(일치)와 공급처 ID(일치)로 데이터 검색
//        List<MaterialEntity> findByStatusAndSupplier_SupplierId(Integer status, Long supplierId);
//        // 창고명(포함)과 공급처 ID(일치)로 데이터 검색
//        List<MaterialEntity> findByWarehouseContainingAndSupplier_SupplierId(String warehouse, Long supplierId);
//
//    // --- 3가지 조건 조합 (10개) ---
//        // 자재명(포함), 카테고리(포함), 상태(일치)로 데이터 검색
//        List<MaterialEntity> findByMaterialNmContainingAndCategoryContainingAndStatus(String materialNm, String category, Integer status);
//        // 자재명(포함), 카테고리(포함), 창고명(포함)으로 데이터 검색
//        List<MaterialEntity> findByMaterialNmContainingAndCategoryContainingAndWarehouseContaining(String materialNm, String category, String warehouse);
//        // 자재명(포함), 카테고리(포함), 공급처 ID(일치)로 데이터 검색
//        List<MaterialEntity> findByMaterialNmContainingAndCategoryContainingAndSupplier_SupplierId(String materialNm, String category, Long supplierId);
//        // 자재명(포함), 상태(일치), 창고명(포함)으로 데이터 검색
//        List<MaterialEntity> findByMaterialNmContainingAndStatusAndWarehouseContaining(String materialNm, Integer status, String warehouse);
//        // 자재명(포함), 상태(일치), 공급처 ID(일치)로 데이터 검색
//        List<MaterialEntity> findByMaterialNmContainingAndStatusAndSupplier_SupplierId(String materialNm, Integer status, Long supplierId);
//        // 자재명(포함), 창고명(포함), 공급처 ID(일치)로 데이터 검색
//        List<MaterialEntity> findByMaterialNmContainingAndWarehouseContainingAndSupplier_SupplierId(String materialNm, String warehouse, Long supplierId);
//        // 카테고리(포함), 상태(일치), 창고명(포함)으로 데이터 검색
//        List<MaterialEntity> findByCategoryContainingAndStatusAndWarehouseContaining(String category, Integer status, String warehouse);
//        // 카테고리(포함), 상태(일치), 공급처 ID(일치)로 데이터 검색
//        List<MaterialEntity> findByCategoryContainingAndStatusAndSupplier_SupplierId(String category, Integer status, Long supplierId);
//        // 카테고리(포함), 창고명(포함), 공급처 ID(일치)로 데이터 검색
//        List<MaterialEntity> findByCategoryContainingAndWarehouseContainingAndSupplier_SupplierId(String category, String warehouse, Long supplierId);
//        // 상태(일치), 창고명(포함), 공급처 ID(일치)로 데이터 검색
//        List<MaterialEntity> findByStatusAndWarehouseContainingAndSupplier_SupplierId(Integer status, String warehouse, Long supplierId);
//
//    // --- 4가지 조건 조합 (5개) ---
//        // 자재명(포함), 카테고리(포함), 상태(일치), 창고명(포함)으로 데이터 검색
//        List<MaterialEntity> findByMaterialNmContainingAndCategoryContainingAndStatusAndWarehouseContaining(String materialNm, String category, Integer status, String warehouse);
//        // 자재명(포함), 카테고리(포함), 상태(일치), 공급처 ID(일치)로 데이터 검색
//        List<MaterialEntity> findByMaterialNmContainingAndCategoryContainingAndStatusAndSupplier_SupplierId(String materialNm, String category, Integer status, Long supplierId);
//        // 자재명(포함), 카테고리(포함), 창고명(포함), 공급처 ID(일치)로 데이터 검색
//        List<MaterialEntity> findByMaterialNmContainingAndCategoryContainingAndWarehouseContainingAndSupplier_SupplierId(String materialNm, String category, String warehouse, Long supplierId);
//        // 자재명(포함), 상태(일치), 창고명(포함), 공급처 ID(일치)로 데이터 검색
//        List<MaterialEntity> findByMaterialNmContainingAndStatusAndWarehouseContainingAndSupplier_SupplierId(String materialNm, Integer status, String warehouse, Long supplierId);
//        // 카테고리(포함), 상태(일치), 창고명(포함), 공급처 ID(일치)로 데이터 검색
//        List<MaterialEntity> findByCategoryContainingAndStatusAndWarehouseContainingAndSupplier_SupplierId(String category, Integer status, String warehouse, Long supplierId);
//
//    // --- 5가지 조건 조합 (1개) ---
//        // 자재명(포함), 카테고리(포함), 상태(일치), 창고명(포함), 공급처 ID(일치)로 데이터 검색
//        List<MaterialEntity> findByMaterialNmContainingAndCategoryContainingAndStatusAndWarehouseContainingAndSupplier_SupplierId(String materialNm, String category, Integer status, String warehouse, Long supplierId);
//}
