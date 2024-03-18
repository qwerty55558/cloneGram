package com.jsy.clonegram.controller.restcontroller;

import com.jsy.clonegram.service.UserPictureService;
import com.jsy.clonegram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UploadController {

    private final UserPictureService pictureService;

    @PostMapping("/upload/profilepic")
    public void setProfilePic(@RequestParam("file")MultipartFile file){
        log.info("send file = {}",file.getName());
        pictureService.setPicture(file);
    }
}
