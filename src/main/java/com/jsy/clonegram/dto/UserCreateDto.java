package com.jsy.clonegram.dto;

import com.jsy.clonegram.dao.Grade;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserCreateDto {

    @NotBlank
    private String userName;
    @jakarta.validation.constraints.Email
    @NotBlank
    private String Email;
    @NotBlank
    private String password;
    private Grade grade;

}
