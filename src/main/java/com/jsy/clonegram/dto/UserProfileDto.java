package com.jsy.clonegram.dto;

import lombok.Data;

@Data
public class UserProfileDto {
    private Long userId;
    private String userName;
    private String userPic;
}
