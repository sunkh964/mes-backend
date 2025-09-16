package com.example.mes_backend.controller;

import com.example.mes_backend.dto.MaterialDto;
import com.example.mes_backend.repository.MaterialRepository;
import com.example.mes_backend.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/materials")
public class MaterialController{
    private final MaterialService materialService;

    @GetMapping
    public List<MaterialDto> getMaterials(
            @RequestParam(required = false) String materialNm,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String warehouse,
            @RequestParam(required = false) Long supplierId
    )  {
        // --- 5가지 조건 ---
        if (materialNm != null && category != null && status != null && warehouse != null && supplierId != null) {
            return materialService.findByMaterialNmContainingAndCategoryContainingAndStatusAndWarehouseContainingAndSupplier_SupplierId(materialNm, category, status, warehouse, supplierId);
        }
        // --- 4가지 조건 ---
        else if (materialNm != null && category != null && status != null && warehouse != null) {
            return materialService.findByMaterialNmContainingAndCategoryContainingAndStatusAndWarehouseContaining(materialNm, category, status, warehouse);
        } else if (materialNm != null && category != null && status != null && supplierId != null) {
            return materialService.findByMaterialNmContainingAndCategoryContainingAndStatusAndSupplier_SupplierId(materialNm, category, status, supplierId);
        } else if (materialNm != null && category != null && warehouse != null && supplierId != null) {
            return materialService.findByMaterialNmContainingAndCategoryContainingAndWarehouseContainingAndSupplier_SupplierId(materialNm, category, warehouse, supplierId);
        } else if (materialNm != null && status != null && warehouse != null && supplierId != null) {
            return materialService.findByMaterialNmContainingAndStatusAndWarehouseContainingAndSupplier_SupplierId(materialNm, status, warehouse, supplierId);
        } else if (category != null && status != null && warehouse != null && supplierId != null) {
            return materialService.findByCategoryContainingAndStatusAndWarehouseContainingAndSupplier_SupplierId(category, status, warehouse, supplierId);
        }
        // --- 3가지 조건 ---
        else if (materialNm != null && category != null && status != null) {
            return materialService.findByMaterialNmContainingAndCategoryContainingAndStatus(materialNm, category, status);
        } else if (materialNm != null && category != null && warehouse != null) {
            return materialService.findByMaterialNmContainingAndCategoryContainingAndWarehouseContaining(materialNm, category, warehouse);
        } else if (materialNm != null && category != null && supplierId != null) {
            return materialService.findByMaterialNmContainingAndCategoryContainingAndSupplier_SupplierId(materialNm, category, supplierId);
        } else if (materialNm != null && status != null && warehouse != null) {
            return materialService.findByMaterialNmContainingAndStatusAndWarehouseContaining(materialNm, status, warehouse);
        } else if (materialNm != null && status != null && supplierId != null) {
            return materialService.findByMaterialNmContainingAndStatusAndSupplier_SupplierId(materialNm, status, supplierId);
        } else if (materialNm != null && warehouse != null && supplierId != null) {
            return materialService.findByMaterialNmContainingAndWarehouseContainingAndSupplier_SupplierId(materialNm, warehouse, supplierId);
        } else if (category != null && status != null && warehouse != null) {
            return materialService.findByCategoryContainingAndStatusAndWarehouseContaining(category, status, warehouse);
        } else if (category != null && status != null && supplierId != null) {
            return materialService.findByCategoryContainingAndStatusAndSupplier_SupplierId(category, status, supplierId);
        } else if (category != null && warehouse != null && supplierId != null) {
            return materialService.findByCategoryContainingAndWarehouseContainingAndSupplier_SupplierId(category, warehouse, supplierId);
        } else if (status != null && warehouse != null && supplierId != null) {
            return materialService.findByStatusAndWarehouseContainingAndSupplier_SupplierId(status, warehouse, supplierId);
        }
        // --- 2가지 조건 ---
        else if (materialNm != null && category != null) {
            return materialService.findByMaterialNmContainingAndCategoryContaining(materialNm, category);
        } else if (materialNm != null && status != null) {
            return materialService.findByMaterialNmContainingAndStatus(materialNm, status);
        } else if (materialNm != null && warehouse != null) {
            return materialService.findByMaterialNmContainingAndWarehouseContaining(materialNm, warehouse);
        } else if (materialNm != null && supplierId != null) {
            return materialService.findByMaterialNmContainingAndSupplier_SupplierId(materialNm, supplierId);
        } else if (category != null && status != null) {
            return materialService.findByCategoryContainingAndStatus(category, status);
        } else if (category != null && warehouse != null) {
            return materialService.findByCategoryContainingAndWarehouseContaining(category, warehouse);
        } else if (category != null && supplierId != null) {
            return materialService.findByCategoryContainingAndSupplier_SupplierId(category, supplierId);
        } else if (status != null && warehouse != null) {
            return materialService.findByStatusAndWarehouseContaining(status, warehouse);
        } else if (status != null && supplierId != null) {
            return materialService.findByStatusAndSupplier_SupplierId(status, supplierId);
        } else if (warehouse != null && supplierId != null) {
            return materialService.findByWarehouseContainingAndSupplier_SupplierId(warehouse, supplierId);
        }
        // --- 1가지 조건 ---
        else if (materialNm != null) {
            return materialService.findByMaterialNmContaining(materialNm);
        } else if (category != null) {
            return materialService.findByCategoryContaining(category);
        } else if (status != null) {
            return materialService.findByStatus(status);
        } else if (warehouse != null) {
            return materialService.findByWarehouseContaining(warehouse);
        } else if (supplierId != null) {
            return materialService.findBySupplier_SupplierId(supplierId);
        }
        // --- 조건 없음 ---
        else {
            return materialService.findAll();
        }
    }
}
