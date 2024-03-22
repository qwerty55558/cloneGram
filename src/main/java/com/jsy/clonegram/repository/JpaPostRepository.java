package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Post;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.PostUpdateDto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@Transactional
public class JpaPostRepository implements PostRepository {
    private final EntityManager em;


    public JpaPostRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    @Override
    public Optional<Post> findById(Long postId) {
        Post post = em.find(Post.class, postId);
        return Optional.ofNullable(post);
    }

    @Override
    public void update(Long postId, PostUpdateDto postUpdateDto) {
        Post post = em.find(Post.class, postId);
        post.setCaption(postUpdateDto.getCaption());
        post.setImageUrl(postUpdateDto.getImageUrl());
    }

    @Override
    public void delete(Long postId) {
        Post post = em.find(Post.class, postId);
        em.remove(post);
    }

    @Override
    public Long findUserIdByPostId(Long postId) {
        Optional<Post> byId = findById(postId);
        return byId.map(Post::getUserId).orElse(null);
    }

    @Override
    public List<Post> findPostsById(Long userId) {
        String jpql = "SELECT p FROM Post p WHERE p.userId = :userId";
        return em.createQuery(jpql, Post.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
