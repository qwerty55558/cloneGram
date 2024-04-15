package com.jsy.clonegram.dto;

import lombok.Data;

@Data
public class UserMessageDto {
    private Boolean mine;
    private String content;

    public UserMessageDto(Boolean mine, String content) {
        this.mine = mine;
        this.content = content;
    }
}
