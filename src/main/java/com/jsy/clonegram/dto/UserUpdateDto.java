package com.jsy.clonegram.dto;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String Email;
    private String password;
    private String fullName;
    private String bio;
    private String profileImageUrl;
}
