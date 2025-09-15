package com.example.mes_backend.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data //getter/setter/toString/Equals/HashCode
@Entity
@Table(name="processes")
public class ProcessEntity {
    @Id
    @Column(name="process_id", length = 20,nullable = false)
    private String processId;

    @Column(name="process_nm", length = 20, nullable = false)
    private String processNm;

    @Column(name = "process_info", length = 200)
    private String processInfo;

    @Column(name = "process_sequence")
    private Integer  processSequence;

    @Column(name = "standard_time", nullable = false)
    private Integer standardTime;

    @Column(name = "is_active")
    private Boolean isActive = true; // 기본값 true

    @Column(name = "remark", length = 255)
    private String remark;

    @CreationTimestamp // 시간 자동 저장
    @Column(name = "created_at", updatable = false) // 생성 시간은 업데이트되지 않도록 설정합니다.
    private LocalDateTime createdAt;

    @UpdateTimestamp // 업데이트될 때 자동 갱신
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
