package com.macv.sprint1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    private int user_id;
    private String date;
    private ProductRequestDto product;
    private int category;
    private double price;

}
