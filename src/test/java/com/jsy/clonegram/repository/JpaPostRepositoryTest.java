package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
@Slf4j
class JpaPostRepositoryTest {

    @Autowired
    PostRepository rep;

    @Test
    void save() {
        for (int i = 0; i < 30; i++) {
            Post post = new Post();
            post.setImageUrl("https://res.cloudinary.com/degplajej/image/upload/h_200,w_200/v1710753943/images/asdf/asdf_pic");
            post.setCaption("이것이 캡션이다 와우 와우 와우");
            post.setUserId(2L);
            rep.save(post);
        }
    }

    @Test
    void findRandPost(){
        List<Post> withPagination = rep.findWithPagination(12);
        log.info("page = {}",withPagination);
    }

    @Test
    void findCondPost(){
        List<Post> caption = rep.findPosts(12, "캡션이다");
        log.info("caption = {}", caption);
    }

    @Test
    void findCondPost2(){
        Pageable createdAt = PageRequest.of(0, 10, Sort.by("created_at").descending());
        Page<Post> withCond = rep.findPosts(createdAt,"캡션");
        log.info("withCond = {}", withCond.iterator().next());
    }
}