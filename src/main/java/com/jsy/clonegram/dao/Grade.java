package com.jsy.clonegram.dao;

/**
 * 유저의 등급을 나타내는 enum
 * 데이터베이스에는 code로 저장된다.
 */

public enum Grade {
    Admin("USER000"),
    Bronze("USER001"),
    Silver("USER002"),
    Gold("USER003"),
    Platinum("USER004"),
    Diamond("USER005");

    private String code;

    Grade(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
