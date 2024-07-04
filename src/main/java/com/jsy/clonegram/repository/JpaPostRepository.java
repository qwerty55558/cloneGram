package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Post;
import com.jsy.clonegram.dto.PostUpdateDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@EnableTransactionManagement
@Transactional(readOnly = true)
public class JpaPostRepository implements PostRepository {
    private final EntityManager em;


    public JpaPostRepository(EntityManager em) {
        this.em = em;
    }


    @Transactional
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

    @Override
    public List findWithPagination(Integer pageSize) {
        String sql = "SELECT p FROM Post p ORDER BY RAND() LIMIT :pageSize";
        return em.createQuery(sql, Post.class)
                .setParameter("pageSize", pageSize)
                .getResultList();

    }

    @Override
    public List findPosts(Integer pageSize, String cond) {
        String sql = "SELECT p FROM Post p WHERE LOWER(caption) LIKE LOWER(:cond) LIMIT :pageSize";
        return em.createQuery(sql, Post.class)
                .setParameter("cond", "%" + cond + "%")
                .setParameter("pageSize", pageSize)
                .getResultList();
    }

    @Override
    public Page<Post> findPosts(Pageable pageable, String cond) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Post> query = criteriaBuilder.createQuery(Post.class);
        Root<Post> from = query.from(Post.class);

        query.select(from);

        if (cond != null && !cond.isEmpty()) {
            Predicate keywordPredicate = criteriaBuilder.like(criteriaBuilder.lower(from.get("caption")), "%" + cond.toLowerCase() + "%");
            query.where(keywordPredicate);
        }

        TypedQuery<Post> postTypedQuery = em.createQuery(query);

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int offset = pageNumber * pageSize;

        postTypedQuery.setFirstResult(offset);
        postTypedQuery.setMaxResults(pageSize);

        List<Post> resultList = postTypedQuery.getResultList();

        // count 쿼리를 생성합니다. 기존의 CriteriaQuery를 재활용하여 select 절을 수정합니다.
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Post> countRoot = countQuery.from(Post.class);
        if (cond != null && !cond.isEmpty()) {
            countQuery.select(criteriaBuilder.count(countRoot)).where(criteriaBuilder.like(criteriaBuilder.lower(countRoot.get("caption")), "%" + cond.toLowerCase() + "%"));
        } else {
            countQuery.select(criteriaBuilder.count(countRoot));
        }
        Long singleResult = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, pageable, singleResult);
    }

    @Override
    public Page<Post> findPosts(Pageable pageable) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Post> query = criteriaBuilder.createQuery(Post.class);
        Root<Post> from = query.from(Post.class);

        query.select(from);

        TypedQuery<Post> postTypedQuery = em.createQuery(query);

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int offset = pageNumber * pageSize;

        postTypedQuery.setFirstResult(offset);
        postTypedQuery.setMaxResults(pageSize);

        List<Post> resultList = postTypedQuery.getResultList();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Post.class)));
        Long singleResult = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, pageable, singleResult);
    }
}
