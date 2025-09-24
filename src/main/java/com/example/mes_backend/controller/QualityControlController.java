package com.example.mes_backend.controller;

import com.example.mes_backend.dto.QualityControlDto;
import com.example.mes_backend.service.QualityControlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/qualityControl")
@RequiredArgsConstructor
public class QualityControlController {

    private final QualityControlService qualityControlService;

//    @PostMapping("/webhook")
//    public ResponseEntity<Void> receiveFromErp(@RequestBody List<QualityControlDto> qualityControlDtos) {
//        qualityControlService.saveInspections(qualityControlDtos);
//        return ResponseEntity.noContent().build();
//    }


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

    /** 조건 검색 */
    @GetMapping("/search")
    public List<QualityControlDto> search(
            @RequestParam(required = false) String purchaseOrderId,
            @RequestParam(required = false) Integer materialId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inspectionDateFrom,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inspectionDateTo,
            @RequestParam(required = false) String result
    ) {
        log.info("QualityControl 조건 검색: purchaseOrderId={}, materialId={}, from={}, to={}, result={}",
                purchaseOrderId, materialId, inspectionDateFrom, inspectionDateTo, result);

        return qualityControlService.search(purchaseOrderId, materialId, inspectionDateFrom, inspectionDateTo, result);
    }

    // ----- 등록 -----
    @PostMapping
    public QualityControlDto create(@RequestBody QualityControlDto dto) {
        return qualityControlService.create(dto);
    }

    // ----- 삭제 -----
    @DeleteMapping("/{qcId}")
    public void delete(@PathVariable("qcId") Integer qcId) {
        qualityControlService.delete(qcId);
    }

    // ----- 수정 -----
    @PutMapping("/{qcId}")
    public QualityControlDto update(
            @PathVariable("qcId") Integer qcId,
            @RequestBody QualityControlDto dto
    ) {
        return qualityControlService.update(qcId, dto);
    }
}