package com.example.mes_backend.service;

import com.example.mes_backend.dto.BlockQCDto;
import com.example.mes_backend.entity.BlockQCEntity;
import com.example.mes_backend.repository.BlockQCRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlockQCService {

    private final BlockQCRepository blockQCRepository;

    // 전체 조회
    public List<BlockQCDto> getAll() {
        return blockQCRepository.findAll()
                .stream()
                .map(BlockQCDto::fromEntity)
                .toList();
    }

    // 조건 검색 (블록 ID, 검사 결과, 검사일자 범위)
    @Transactional
    public List<BlockQCDto> search(Integer blockId,
                                   String result,
                                   LocalDateTime from,
                                   LocalDateTime to) {

        return blockQCRepository.findAll((root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();

                    // 블록 ID 조건
                    if (blockId != null) {
                        predicates.add(cb.equal(root.get("block").get("blockId"), blockId));
                    }

                    // 검사 결과 조건 (대소문자 무시)
                    if (result != null && !result.isEmpty()) {
                        predicates.add(cb.equal(cb.upper(root.get("result")), result.toUpperCase()));
                    }

                    // 검사일자 시작 조건
                    if (from != null) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("inspectionDate"), from));
                    }

                    // 검사일자 종료 조건
                    if (to != null) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("inspectionDate"), to));
                    }

                    return cb.and(predicates.toArray(new Predicate[0]));
                })
                .stream()
                .map(BlockQCDto::fromEntity)
                .toList();
    }
}
