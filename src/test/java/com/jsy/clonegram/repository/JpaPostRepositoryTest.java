package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class JpaPostRepositoryTest {

    @Autowired
    PostRepository rep;

    @Test
    void save() {
        Post post = new Post();
        post.setImageUrl("https://res.cloudinary.com/degplajej/image/upload/h_200,w_200/v1710753943/images/asdf/asdf_pic");
        post.setCaption("이것이 캡션이다 와우 와우 와우");
        post.setUserId(2L);
        rep.save(post);
        log.info("Post = {}",post);
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findPostsById() {
    }
}