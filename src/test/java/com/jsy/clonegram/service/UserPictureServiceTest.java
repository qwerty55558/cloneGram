package com.jsy.clonegram.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Search;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
@Slf4j
class UserPictureServiceTest {


    @Autowired
    UserPictureService pictureService;
    @Autowired
    Cloudinary cloudinary;
    @Autowired
    UserService userService;

    @Test
    void getPictureUrl() {
        String pictureUrl = pictureService.getPictureUrl();
        log.info("url = {}",pictureUrl);
    }

    @Test
    void getFolder(){
        try {
            String testuser = "test";
            ApiResponse images = cloudinary.api().createFolder("images/" + testuser, ObjectUtils.emptyMap());
            log.info("result = {}",images.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}