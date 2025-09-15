package com.example.mes_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @Column(name = "customer_id", length = 20, nullable = false)
    private String customerId;

    @Column(name = "customer_nm", length = 50, nullable = false)
    private String customerName;

    @Column(name = "business_registration", length = 30, nullable = false, unique = true)
    private String businessRegistration;

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Column(name = "country_code", length = 2, nullable = false)
    private String countryCode = "KR";

    @Column(name = "status", length = 20, nullable = false)
    private String status = "ACTIVE";

    @Column(name = "contact_person", length = 20)
    private String contactPerson;

    @Column(name = "contact_phone", length = 50)
    private String contactPhone;

    @Column(name = "contact_email", length = 50)
    private String contactEmail;

    @Column(name = "contact_address", length = 100)
    private String contactAddress;

    @Column(name = "remark", length = 255)
    private String remark;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 생성일

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 수정일
}
