package com.macv.sprint1.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {

    private int productId;
    private String productName;
    private String type;
    private String brand;
    private String color;
    private String notes;

}
