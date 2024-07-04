package com.jsy.clonegram.controller.restcontroller;

import com.jsy.clonegram.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UploadController {
    private final UploadService uploadService;

    @PostMapping("/upload/profilepic")
    public void setProfilePic(@RequestParam(name = "file") MultipartFile file) {
        uploadService.setProfilePic(file);
    }

    @PostMapping("/upload/postpic")
    public void setPostPic(@RequestPart(name = "file") MultipartFile file, @RequestParam(name = "caption") String caption) {
        uploadService.setPostPic(file, caption);
    }
}
