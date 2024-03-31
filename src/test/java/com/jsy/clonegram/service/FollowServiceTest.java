package com.jsy.clonegram.service;

import com.jsy.clonegram.dao.Follow;
import com.jsy.clonegram.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
class FollowServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FollowService followService;

    @Test
    void checkFollowStatus() {
        followService.followUser(2L,3L);
        Optional<Follow> first = userRepository.findFollowingsByUserId(2L).stream().filter(c -> c.getUserId().equals(3L)).findFirst();
        log.info("first = {}",first);
    }

    @Test
    void repSizeTest(){
        int size = userRepository.findFollowingsByUserId(2L).size();
        Long followingSize = (long) userRepository.findFollowingsByUserId(2L).size();
        log.info("size = {}",size);
        log.info("longsize = {}",followingSize);

    }
}