package com.example.mes_backend.controller;

import com.example.mes_backend.dto.WorkCenterDto;
import com.example.mes_backend.service.WorkCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/work-centers")
@RequiredArgsConstructor
public class WorkCenterController {

    private final WorkCenterService workCenterService;

    @GetMapping
    public List<WorkCenterDto> getWorkCenters(
            @RequestParam(required = false) String workCenterId,
            @RequestParam(required = false) String workCenterNm,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) String processId
    ) {
        // 4가지 조건 모두 있는 경우
        if (workCenterId != null && workCenterNm != null && isActive != null && processId != null) {
            return workCenterService.findByWorkCenterIdContainingAndWorkCenterNmContainingAndIsActiveAndProcessId(workCenterId, workCenterNm, isActive, processId);
        }
        // 3가지 조건 조합
        else if (workCenterId != null && workCenterNm != null && processId != null) {
            return workCenterService.findByWorkCenterIdContainingAndWorkCenterNmContainingAndProcessId(workCenterId, workCenterNm, processId);
        } else if (workCenterId != null && isActive != null && processId != null) {
            return workCenterService.findByWorkCenterIdContainingAndIsActiveAndProcessId(workCenterId, isActive, processId);
        } else if (workCenterNm != null && isActive != null && processId != null) {
            return workCenterService.findByWorkCenterNmContainingAndIsActiveAndProcessId(workCenterNm, isActive, processId);
        } else if (workCenterId != null && workCenterNm != null && isActive != null) {
            return workCenterService.findByWorkCenterIdContainingAndWorkCenterNmContainingAndIsActive(workCenterId, workCenterNm, isActive);
        }
        // 2가지 조건 조합
        else if (workCenterId != null && processId != null) {
            return workCenterService.findByWorkCenterIdContainingAndProcessId(workCenterId, processId);
        } else if (workCenterNm != null && processId != null) {
            return workCenterService.findByWorkCenterNmContainingAndProcessId(workCenterNm, processId);
        } else if (isActive != null && processId != null) {
            return workCenterService.findByIsActiveAndProcessId(isActive, processId);
        } else if (workCenterId != null && workCenterNm != null) {
            return workCenterService.findByWorkCenterIdContainingAndWorkCenterNmContaining(workCenterId, workCenterNm);
        } else if (workCenterId != null && isActive != null) {
            return workCenterService.findByWorkCenterIdContainingAndIsActive(workCenterId, isActive);
        } else if (workCenterNm != null && isActive != null) {
            return workCenterService.findByWorkCenterNmContainingAndIsActive(workCenterNm, isActive);
        }
        // 1가지 조건
        else if (workCenterId != null) {
            return workCenterService.findByWorkCenterIdContaining(workCenterId);
        } else if (workCenterNm != null) {
            return workCenterService.findByWorkCenterNmContaining(workCenterNm);
        } else if (isActive != null) {
            return workCenterService.findByIsActive(isActive);
        } else if (processId != null) {
            return workCenterService.findByProcessId(processId);
        }
        // 조건이 없을 경우 (전체 조회)
        else {
            return workCenterService.getAllSearch();
        }
    }

    // 등록
    @PostMapping
    public WorkCenterDto createWorkCenter(@RequestBody WorkCenterDto dto){
        return workCenterService.create(dto);
    }

    // 삭제
    @DeleteMapping("/{workCenterId}")
    public void deleteWorkCenter(@PathVariable("workCenterId") String workCenterId){
        workCenterService.delete(workCenterId);
    }

}
