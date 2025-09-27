package com.example.mes_backend.dto;

import lombok.Data;

@Data
public class SimpleStockRequestDto {
    private Integer materialId;
    private int quantity;
}
