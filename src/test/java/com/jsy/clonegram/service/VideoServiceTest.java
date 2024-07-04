package com.jsy.clonegram.service;

import com.jsy.clonegram.dao.VideoPost;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;


@Slf4j
@SpringBootTest
class VideoServiceTest {

    @Autowired
    private VideoService videoService;

    @Test
    void getVideoPosts() {
        Page<VideoPost> videoPosts = videoService.getVideoPosts(0, 3);
        videoPosts.stream().forEach(videoPost -> {
            log.info(videoPost.toString());
        });
    }

    @Test
    void getReels(){
        Page<VideoPost> reelsList = videoService.getReelsList(2L, 0, 5);
        reelsList.stream().forEach(videoPost -> {
            log.info(videoPost.toString());
        });
    }
}