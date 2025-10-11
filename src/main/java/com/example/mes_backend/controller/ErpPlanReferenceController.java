package com.example.mes_backend.controller;

import com.example.mes_backend.dto.PlanIdDto;
import com.example.mes_backend.service.ErpPlanReferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/erp-plan-refs")
@RequiredArgsConstructor
public class ErpPlanReferenceController {

    private final ErpPlanReferenceService service;

    @PostMapping
    public ResponseEntity<Void> receivePlanId(@RequestBody PlanIdDto dto) {
        service.savePlanId(dto);
        return ResponseEntity.ok().build();
    }
}