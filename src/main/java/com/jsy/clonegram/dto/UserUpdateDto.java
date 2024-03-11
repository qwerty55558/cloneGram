package com.jsy.clonegram.dto;

import com.jsy.clonegram.dao.Grade;
import lombok.Data;

/**
 * User Update를 위한 DTO
 */
@Data
public class UserUpdateDto {
    private Long TargetId;
    private String Email;
    private String password;
    private String fullName;
    private String bio;
    private String profileImageUrl;
    private Grade grade;

    public UserUpdateDto() {
    }
}
