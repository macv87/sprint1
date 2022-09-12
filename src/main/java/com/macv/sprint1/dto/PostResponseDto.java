package com.macv.sprint1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private int user_id;
    private int post_id;
    private String date;
    private ProductRequestDto product;
    private int category;
    private double price;

}
