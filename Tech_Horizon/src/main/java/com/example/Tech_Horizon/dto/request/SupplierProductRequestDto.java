package com.example.Tech_Horizon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierProductRequestDto
{
    private String productName;
    private String category;
    private Double price;
    private String unit;
    private Long stockAvailable;
}
