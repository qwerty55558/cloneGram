package com.jsy.clonegram.dao;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Likey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;
    private Long postId;
    private Long userId;

}
