package com.jsy.clonegram.dao;

import lombok.Data;

@Data
public class Follow {
    private Long userId;
    private Long followerId;
}
