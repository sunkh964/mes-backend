package com.example.mes_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @Column(name = "project_id", length = 20, nullable = false)
    private String projectId;

    @Column(name = "project_nm", length = 50, nullable = false)
    private String projectName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "actual_delivery_date")
    private LocalDate actualDeliveryDate;

    @Column(name = "total_budget", precision = 20, scale = 2)
    private BigDecimal totalBudget;

    @Column(name = "execution_budget", precision = 20, scale = 2)
    private BigDecimal executionBudget;

    @Column(name = "currency_code", length = 3, nullable = false)
    private String currencyCode = "KRW";

    @Column(name = "progress_rate", precision = 5, scale = 2, nullable = false)
    private BigDecimal progressRate = BigDecimal.ZERO;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Column(name = "remark", length = 255)
    private String remark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
