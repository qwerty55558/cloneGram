package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentRepository extends JpaRepository<Comment, Long> {

}
