package com.jsy.clonegram.controller.restcontroller;

import com.jsy.clonegram.dao.Post;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.PostUpdateDto;
import com.jsy.clonegram.repository.PostRepository;
import com.jsy.clonegram.repository.UserRepository;
import com.jsy.clonegram.service.UserPictureService;
import com.jsy.clonegram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UploadController {

    private final UserPictureService pictureService;
    private final PostRepository postRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/upload/profilepic")
    public void setProfilePic(@RequestParam(name = "file") MultipartFile file) {
        log.info("send file = {}", file.getName());
        pictureService.setPicture(file);
    }

    @PostMapping("/upload/postpic")
    public void setPostPic(@RequestPart(name = "file") MultipartFile file, @RequestParam(name = "caption") String caption) {
        Post post = new Post();
        post.setCaption(caption);
        String usernameOnSession = userService.getUsernameOnSession();
        Optional<User> byName = userRepository.findByName(usernameOnSession);
        if (byName.isPresent()) {
            Long id = byName.get().getId();
            post.setUserId(id);
        }
        Post save = postRepository.save(post);
        Long id = save.getId();
        pictureService.setPostPicture(file, id);
        String postSizePicById = pictureService.getPostSizePicById(id);
        PostUpdateDto postUpdateDto = new PostUpdateDto(caption,postSizePicById);
        postRepository.update(id, postUpdateDto);
    }
}
