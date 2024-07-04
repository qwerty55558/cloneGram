package com.jsy.clonegram.dao;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "post")
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "post_id")
    private Long id;
    // 관례의 불일치 자동 해소 (Column)
    private Long userId;
    private String caption;
    private String imageUrl;

    public Post() {
    }
}
