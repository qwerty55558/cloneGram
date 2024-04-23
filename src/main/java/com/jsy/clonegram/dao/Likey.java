package com.jsy.clonegram.dao;

import com.jsy.clonegram.repository.listener.LikeyListener;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@EntityListeners(LikeyListener.class)
public class Likey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;
    private Long postId;
    private Long userId;

}
