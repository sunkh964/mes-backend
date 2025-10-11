package com.example.mes_backend.service;

import com.example.mes_backend.dto.PlanIdDto;
import com.example.mes_backend.entity.ErpPlanReference;
import com.example.mes_backend.repository.ErpPlanReferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ErpPlanReferenceService {

    private final ErpPlanReferenceRepository repository;

    public void savePlanId(PlanIdDto dto) {
        // 이미 저장된 ID인지 확인하여 중복 저장을 방지할 수 있습니다.
        if (!repository.existsById(dto.getPlanId())) {
            ErpPlanReference newRef = new ErpPlanReference(dto.getPlanId());
            repository.save(newRef);
        }
    }
}
