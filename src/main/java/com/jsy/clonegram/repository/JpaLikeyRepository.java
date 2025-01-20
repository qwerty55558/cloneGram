package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Likey;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;


@EnableJpaRepositories
public interface JpaLikeyRepository extends JpaRepository<Likey, Long> {
    Optional<Likey> findByUserIdAndPostId(Long userId, Long postId);
    @Transactional
    Optional<Likey> deleteByUserIdAndPostId(Long userId, Long postId);
    Optional<Long> countByPostId(Long postId);
}
