package com.example.mes_backend.service;

import com.example.mes_backend.dto.ShipmentsDto;
import com.example.mes_backend.repository.ShipmentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShipmentsService {
    private final ShipmentsRepository shipmentsRepository;

    //전체 조회
    public List<ShipmentsDto> getAll(){
        return shipmentsRepository.findAll()
                .stream()
                .map(ShipmentsDto::fromEntity)
                .toList();
    }
}
