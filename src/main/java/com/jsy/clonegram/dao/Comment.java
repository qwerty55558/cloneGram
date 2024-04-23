package com.jsy.clonegram.dao;

import com.jsy.clonegram.repository.listener.CommentListener;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@EntityListeners(CommentListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private Long postId;
    private Long userId;
    private String content;
}
