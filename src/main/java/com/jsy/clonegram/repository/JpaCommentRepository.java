package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface JpaCommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByUserId(Long UserId);

    List<Comment> findCommentsByPostId(Long PostId);
}
