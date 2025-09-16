package com.example.mes_backend.service;

import com.example.mes_backend.dto.WorkOrderDto;
import com.example.mes_backend.entity.WorkOrderEntity;
import com.example.mes_backend.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkOrderService {

    private final WorkOrderRepository workOrderRepository;

    // 전체 조회
    public List<WorkOrderDto> getAll() {
        return workOrderRepository.findAll()
                .stream()
                .map(WorkOrderDto::fromEntity)
                .toList();
    }

}
