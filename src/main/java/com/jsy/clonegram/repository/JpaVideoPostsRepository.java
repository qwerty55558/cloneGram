package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.VideoPost;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaVideoPostsRepository extends JpaRepository<VideoPost, Long> {
    List<VideoPost> findAllByUserId(Long userId, Sort sort);
    List<VideoPost> findAllByUserId(Long userId);
}
