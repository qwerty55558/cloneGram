package com.jsy.clonegram.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

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
//        String pictureUrl = pictureService.getMiniPicUrl();
        String profilePicUrl = pictureService.getProfilePicUrl();
        log.info("url = {}", profilePicUrl);
    }

    @Test
    void getFolder() {
        try {
            String testuser = "test";
            ApiResponse images = cloudinary.api().createFolder("images/" + testuser, ObjectUtils.emptyMap());
            log.info("result = {}", images.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteUser() {
        try {
            List<String> list = List.of( "qwer", "zxcv");
            for (String s : list) {
                pictureService.deleteUserPicture(s);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deletePost() {
        try {
            List<Long> list = List.of(90L,91L);
            for (Long l : list) {
                pictureService.deletePostPicture(l);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}