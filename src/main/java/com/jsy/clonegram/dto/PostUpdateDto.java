package com.jsy.clonegram.dto;

import lombok.Data;

@Data
public class PostUpdateDto {
    private String caption;
    private String imageUrl;

    public PostUpdateDto(String caption, String imageUrl) {
        this.caption = caption;
        this.imageUrl = imageUrl;
    }

    public PostUpdateDto() {
    }
}
