package com.jsy.clonegram.dto;

import lombok.Data;

@Data
public class DefaultMessageDto {
    private String message;

    public DefaultMessageDto(String message) {
        this.message = message;
    }

    public DefaultMessageDto() {
    }
}
