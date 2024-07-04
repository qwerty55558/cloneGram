package com.jsy.clonegram.controller.restcontroller;

import com.jsy.clonegram.dao.VideoPost;
import com.jsy.clonegram.service.UploadService;
import com.jsy.clonegram.service.UserService;
import com.jsy.clonegram.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class VideoController {
    private final UploadService uploadService;
    private final VideoService videoService;
    private final UserService userService;

    @Value("${VIDEO.URL}")
    private String videoUrl;

    @PostMapping("/video/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("caption") String caption) {
        log.info("file = {}", file);
        log.info("caption = {}", caption);

        return uploadService.saveVideo(file, caption);
    }

    @PostMapping("/video/list")
    public Page<VideoPost> findVideos(@RequestParam("pageNum") Integer pageNum) {
        return videoService.getVideoPosts(pageNum, 1);
    }

    @GetMapping("/video/{videoId}")
    public ResponseEntity<ResourceRegion> downloadVideo(@PathVariable("videoId") Long videoId, @RequestHeader HttpHeaders headers) {
        return videoService.streamingVideo(headers, videoId);
    }

    @GetMapping("/video/null")
    public void downloadVideo() {
    }

    @PostMapping("/reels/list")
    public Page<VideoPost> getReels(@RequestParam("userId") Long userId, @RequestParam("pageNum") Integer pageNum) {
        return videoService.getReelsList(userId, pageNum, 5);
    }

    @PostMapping("myreels/list")
    public Page<VideoPost> getReels(@RequestParam("pageNum") Integer pageNum) {
        return videoService.getReelsList(userService.getUserIdOnSession(), pageNum, 5);
    }

    @PostMapping("video/update")
    public boolean updateVideo(@RequestParam("videoId") Long videoId, @RequestParam("caption") String caption) {
        return videoService.updateCaption(videoId, caption);
    }

    @PostMapping("video/delete")
    public boolean deleteVideo(@RequestParam("videoId") Long videoId) {
        return videoService.deleteReels(videoId);
    }

}

