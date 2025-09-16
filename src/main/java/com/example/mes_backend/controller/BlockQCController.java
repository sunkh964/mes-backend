package com.example.mes_backend.controller;

import com.example.mes_backend.dto.BlockQCDto;
import com.example.mes_backend.service.BlockQCService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blockQC")
public class BlockQCController {

    private final BlockQCService blockQCService;

    // 전체 조회
    @GetMapping("/getAll")
    public List<BlockQCDto> getAllBlockQC() {
        return blockQCService.getAll();
    }

    // 조건 검색 (블록ID, 검사 결과, 검사 일자)
    @GetMapping("/search")
    public List<BlockQCDto> searchBlockQC(
            @RequestParam(required = false) Integer blockId,
            @RequestParam(required = false) String result,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to
    ) {
        return blockQCService.search(blockId, result, from, to);
    }
}
