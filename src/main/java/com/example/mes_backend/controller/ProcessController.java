package com.example.mes_backend.controller;

import com.example.mes_backend.dto.ProcessDto;
import com.example.mes_backend.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/processes")
@RequiredArgsConstructor // Service의 생성자를 자동으로 만들어주는 역할
public class ProcessController {

    private final ProcessService processService;

    // 조건 조회
    @GetMapping
    public List<ProcessDto> getProcesses(
            @RequestParam(required = false) String processId,
            @RequestParam(required = false) String processNm,
            @RequestParam(required = false) Boolean isActive){

        // @RequestParam(required = false)
        // 컨트롤러에서 이 파라미터가 필요없음을 명시.

        // boolean이 아니라 Boolean을 쓴 이유.
        // 컨트롤러에서 boolean은 값이 없는 것인지 false인지 판단 할 수가 없어서 오류 남.
        // Boolean은 true, false가 담겨있는 객체로 컨트롤러가 파라미터가 비어있는지 확인 가능함.

        // processId, processNm에도 붙인 이유
        // 코드의 통일성 & 명확성
        // url에서 받는 파라미터와 Java에서 사용하는 변수명이 다를 때 조정 가능함.

        // 공정아이디, 공정명, 활성 여부 모두 있는 경우
        if (processId != null && processNm != null && isActive != null){
            return processService.getAllSearchProcessIdProcessNmActive(processId, processNm, isActive);
        }
        // 공정아이디, 공정명만 검색한 경우
        else if (processId != null && processNm != null) {
            return processService.findByProcessIdProcessNm(processId, processNm);
        }
        // 공정명, 활성 여부만 검색한 경우
        else if (processNm != null && isActive != null) {
            return processService.getAllSearchProcessNmActive(processNm, isActive);
        }
        // 활성 여부, 공정아이디만 검색한 경우
        else if (processId != null && isActive != null) {
            return processService.getAllSearchProcessIdActive(processId, isActive);
        }
        // 공정아이디만 검색한 경우
        else if (processId != null){
            return processService.getAllSearchProcessId(processId);
        }
        // 공정명만 검색 한 경우
        else if (processNm != null){
            return processService.getAllSearchProcessNm(processNm);
        }
        // 활성 여부만 검색 한 경우
        else if (isActive != null){
            return processService.getAllSearchActive(isActive);
        }
        // 아무 조건이 없을 경우 = 전체 조회
        else {
            return processService.getAllSearch();
        }
    }

    // 단건 조회
    @GetMapping("/{processId}")
    public ProcessDto getProcess(@PathVariable("processId")String process){
        return processService.getProcess(process);
    }



    // ============= 등록 =================
    @PostMapping
    public ProcessDto createProcess(@RequestBody ProcessDto dto) {
        return processService.create(dto);
    }

    // ============= 삭제 =================
    // 삭제
    @DeleteMapping("/{processId}")
    public void deleteProcess(@PathVariable("processId") String processId) {
        processService.delete(processId);
    }


}
