package com.macv.sprint1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowersCountResponseDto {

    private int userId;
    private String userName;
    private long followersCount;

}
