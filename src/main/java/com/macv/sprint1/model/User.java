package com.macv.sprint1.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class User extends Person{

    public User(int id, String name) {
        super(id, name);
    }
}
