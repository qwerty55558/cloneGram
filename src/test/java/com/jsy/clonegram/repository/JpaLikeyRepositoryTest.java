package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Likey;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class JpaLikeyRepositoryTest {

    @Autowired
    JpaLikeyRepository jpaLikeyRepository;

    @Test
    void findLikeyByUserIdAndPostId() {
        Optional<Likey> byUseridAndPostid = jpaLikeyRepository.findByUserIdAndPostId(2L, 1L);
        assertTrue(byUseridAndPostid.isPresent());
    }

    @Test
    void deleteLikeyByUserIdAndPostId() {
        jpaLikeyRepository.deleteByUserIdAndPostId(2L, 1L);
    }
}