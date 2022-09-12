package com.macv.sprint1.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private int productId;
    private String productName;
    private String type;
    private String brand;
    private String color;
    private String notes;
}
