package com.macv.sprint1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Person {

    private int userId;
    private String userName;

}
