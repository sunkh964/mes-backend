package com.example.mes_backend.entity; // 본인의 패키지 경로에 맞게 수정하세요.

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "work_centers")
public class WorkCenterEntity {

    @Id
    @Column(name = "work_center_id", length = 20, nullable = false)
    private String workCenterId;

    @Column(name = "work_center_nm", length = 50, nullable = false)
    private String workCenterNm;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id")
    private ProcessEntity process;

    @Column(name = "remark", length = 255)
    private String remark;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
