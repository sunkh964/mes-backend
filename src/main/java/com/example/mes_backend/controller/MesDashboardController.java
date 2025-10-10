package com.example.mes_backend.controller;

import com.example.mes_backend.service.MesDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class MesDashboardController {

    private final MesDashboardService mesDashboardService;

    //1.MES 대시보드 (ERP 데이터 포함)
    @GetMapping("/project")
    public ResponseEntity<?> getDashboardSummary() {
        return ResponseEntity.ok(mesDashboardService.getErpDashboardSummary());
    }

    //2.주요 고객사 현황
    @GetMapping("/customers")
    public ResponseEntity<?> getErpCustomers() {
        return ResponseEntity.ok(mesDashboardService.getErpDashboardCustomer());
    }

    //3.자재 현황
    @GetMapping("/materials")
    public ResponseEntity<?> getErpMaterials() {
        return ResponseEntity.ok(mesDashboardService.getErpDashboardMaterial());
    }

    //4.구매 현황
    @GetMapping("/purchases")
    public ResponseEntity<?> getDashboardPurchases() {
        return ResponseEntity.ok(mesDashboardService.getErpDashboardPurchase());
    }

    // 5.생산계획 현황
    @GetMapping("/production_plans")
    public ResponseEntity<?> getProductionPlanSummary() {
        return ResponseEntity.ok(mesDashboardService.getErpDashboardProductionPlan());
    }
    // 6. 품질검사 현황 (블록 + 자재)
    @GetMapping("/quality")
    public ResponseEntity<?> getQualitySummary() {
        return ResponseEntity.ok(mesDashboardService.getQualitySummary());
    }

    // 7. 출하 현황 (Shipment)
    @GetMapping("/shipments")
    public ResponseEntity<?> getShipmentSummary() {
        return ResponseEntity.ok(mesDashboardService.getShipmentSummary());
    }

    // 8. 인사 현황 (Employee)
    @GetMapping("/employees")
    public ResponseEntity<?> getEmployeeSummary() {
        return ResponseEntity.ok(mesDashboardService.getEmployeeSummary());
    }

}