package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Post;
import com.jsy.clonegram.dto.PostUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    Optional<Post> findById(Long postId);

    void update(Long postId, PostUpdateDto updateDto);

    void delete(Long postId);

    List<Post> findPostsById(Long userId);

    Long findUserIdByPostId(Long postId);

    List<Post> findWithPagination(Integer pageSize);

    List<Post> findPosts(Integer pageSize, String cond);

    Page<Post> findPosts(Pageable pageable);

    Page<Post> findPosts(Pageable pageable, String cond);
}
