package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.VideoPost;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface JpaVideoPostsRepository extends JpaRepository<VideoPost, Long> {
    List<VideoPost> findAllByUserId(Long userId, Sort sort);
    List<VideoPost> findAllByUserId(Long userId);
}
