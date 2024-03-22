package com.jsy.clonegram.controller;

import com.jsy.clonegram.dao.Post;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.repository.PostRepository;
import com.jsy.clonegram.repository.UserRepository;
import com.jsy.clonegram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class PostControllerTest {

    @Autowired
    PostRepository repository;
    @Autowired
    UserService service;
    @Autowired
    UserRepository userRepository;

    @Test
    void redirectPost() {
        Optional<User> byName = userRepository.findByName("asdf");
        log.info("byname = {}", byName.get().getId());
        List<Post> postsById = repository.findPostsById(byName.get().getId());
        Optional<Post> first = postsById.stream().filter(p -> p.getId().equals(6L)).findFirst();
        log.info("first = {}",first.get());
    }
}