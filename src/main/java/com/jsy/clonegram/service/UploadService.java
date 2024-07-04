package com.jsy.clonegram.service;

import com.jsy.clonegram.dao.Post;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dao.VideoPost;
import com.jsy.clonegram.dto.PostUpdateDto;
import com.jsy.clonegram.repository.JpaVideoPostsRepository;
import com.jsy.clonegram.repository.PostRepository;
import com.jsy.clonegram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UploadService {
    private final UserPictureService pictureService;
    private final PostRepository postRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JpaVideoPostsRepository videoPostsRepository;

    @Value("${VIDEO.URL}")
    String videoUrl;

    public void setProfilePic(MultipartFile file) {
        log.info("send file = {}", file.getName());
        pictureService.setPicture(file);
    }

    public void setPostPic(MultipartFile file, String caption){
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

    public ResponseEntity<String> saveVideo(MultipartFile file, String caption) {
        try {
            Long userIdOnSession = userService.getUserIdOnSession();
            VideoPost videoPost = new VideoPost();
            videoPost.setCaption(caption);
            videoPost.setUserId(userIdOnSession);
            VideoPost saved = videoPostsRepository.save(videoPost);
            Long id = saved.getId();
            File uploadDir = new File(videoUrl + "/" + id);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String type = file.getContentType().split("/")[1];
            File video = new File(videoUrl + "/" + id + "/" + UUID.randomUUID() + "." + type);
            file.transferTo(video);

            saved.setVideoPath("/" + id + "/" + video.getName());
            log.info("saved ={}",saved);
            videoPostsRepository.save(saved);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload Error");
        }
        return ResponseEntity.ok("File upload Success");
    }


}
