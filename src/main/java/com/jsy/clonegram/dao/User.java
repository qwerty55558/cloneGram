package com.jsy.clonegram.dao;

import lombok.Data;

/**
 * User 데이터
 */
@Data
public class User {
    private Long Id;
    private String userName;
    private String Email;
    private String password;
    private String fullName;
    private String bio;
    private String profileImageUrl;

    public User(String id, String pw) {
        this.userName = id;
        this.password = pw;
    }

    public User() {
    }
}
