package com.macv.sprint1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowersListResponseDto {

    private int userId;
    private String userName;
    private List<UserResponseDto> followers;
}
