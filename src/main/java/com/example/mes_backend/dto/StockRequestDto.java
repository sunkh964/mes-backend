
package com.example.mes_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockRequestDto {
    private Integer materialId;
    private String warehouse;
    private String location;
    private int quantity;
}
