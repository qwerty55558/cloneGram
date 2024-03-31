package com.jsy.clonegram.dto;

import lombok.Data;

@Data
public class FollowSetDto {
    private Long follower;
    private Long following;
    private Boolean followCheck;
}
