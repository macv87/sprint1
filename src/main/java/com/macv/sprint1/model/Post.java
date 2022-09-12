package com.macv.sprint1.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Post {

    private int id;
    private int userId;
    private LocalDate date;
    private Product product;
    private int category;
    private double price;

}
