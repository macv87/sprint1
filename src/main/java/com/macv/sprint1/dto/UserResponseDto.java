package com.macv.sprint1.dto;

import com.macv.sprint1.model.Person;
import com.macv.sprint1.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private int userId;
    private String userName;

    public UserResponseDto(Person user){
        userId=user.getId();
        userName=user.getName();
    }
}
