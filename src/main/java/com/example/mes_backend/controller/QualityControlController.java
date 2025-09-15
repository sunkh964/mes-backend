package com.example.mes_backend.controller;

import com.example.mes_backend.dto.QualityControlDto;
import com.example.mes_backend.service.QualityControlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/qualityControl")
@RequiredArgsConstructor
public class QualityControlController {

    private final QualityControlService qualityControlService;

    @PostMapping("/webhook")
    public ResponseEntity<Void> receiveFromErp(@RequestBody List<QualityControlDto> qualityControlDtos) {
        qualityControlService.saveInspections(qualityControlDtos);
        return ResponseEntity.noContent().build();
    }


    // 전체 조회
    @GetMapping
    public List<QualityControlDto> getAll() {
        log.info("select test");
        return qualityControlService.getAll();
    }

    //단건 조회
    @GetMapping("/{qualityControlId}")
    public QualityControlDto getById(Integer qualityControlId){
        return qualityControlService.getById(qualityControlId);
    }

}