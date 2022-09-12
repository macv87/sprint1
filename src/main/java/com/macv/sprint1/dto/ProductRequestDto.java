package com.macv.sprint1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {

    private int product_id;
    private String product_name;
    private String type;
    private String brand;
    private String color;
    private String notes;
}
