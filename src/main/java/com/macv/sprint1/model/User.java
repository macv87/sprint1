package com.macv.sprint1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
public class User extends Person{

    public User(int id, String name) {
        super(id, name);
    }
}
