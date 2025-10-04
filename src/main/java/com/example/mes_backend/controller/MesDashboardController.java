package com.example.mes_backend.controller;

import com.example.mes_backend.service.MesDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class MesDashboardController {

    private final MesDashboardService mesDashboardService;

    // MES 대시보드 (ERP 데이터 포함)
    @GetMapping("/project")
    public ResponseEntity<?> getDashboardSummary() {
        return ResponseEntity.ok(mesDashboardService.getErpDashboardSummary());
    }
}