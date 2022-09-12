package com.macv.sprint1.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Post {

    private int id;
    private int userId;
    private LocalDate date;
    private Product product;
    private int category;
    private double price;

}
