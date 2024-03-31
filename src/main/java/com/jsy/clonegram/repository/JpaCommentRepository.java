package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByUserId(Long UserId);

    List<Comment> findCommentsByPostId(Long PostId);
}
