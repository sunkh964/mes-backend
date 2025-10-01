package com.example.mes_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_logs")
@Data
public class AttendanceLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id", nullable = false)
    private Integer attendanceId;

    // 직원 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;  // employees 테이블과 매핑

    // 근태 날짜
    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    // 근태 상태 1: 출근, 2: 휴무
    @Column(name = "attendance_status", nullable = false)
    private Integer attendanceStatus = 1;

    // 출근 시간
    @Column(name = "check_in")
    private LocalDateTime checkIn;

    // 퇴근 시간
    @Column(name = "check_out")
    private LocalDateTime checkOut;

    // 비고
    @Column(name = "remark", length = 100)
    private String remark;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
