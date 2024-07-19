package com.jsy.clonegram.service;

import com.jsy.clonegram.dao.VideoPost;
import com.jsy.clonegram.repository.JpaVideoPostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class VideoService {
    @Value("${VIDEO.URL}")
    private String videoUrl;

    private final JpaVideoPostsRepository jpaVideoPostsRepository;
    private final UserService userService;
    private final MessageSource messageSource;

    public Page<VideoPost> getVideoPosts(int pageNum, int pageSize, Locale locale) {
        try {
            List<VideoPost> videoPostList = jpaVideoPostsRepository.findAll(Sort.by(Sort.Order.desc("id")));
            if (videoPostList.isEmpty()) {
                VideoPost videoPost = new VideoPost();
                videoPost.setVideoPath("empty/empty");
                videoPost.setCaption(messageSource.getMessage("home.reels.empty",null,null,locale));
                videoPostList.add(videoPost);
                return new PageImpl<>(videoPostList, PageRequest.of(0, 1), 1);
            }
            Pageable pageable = PageRequest.of(pageNum, pageSize);

            int start = (int)pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), videoPostList.size());
            List<VideoPost> videoPostList1 = videoPostList.subList(start, end);
            return new PageImpl<>(videoPostList1, pageable, videoPostList.size());
        }catch (Exception e){
            log.info("Error videoPosts", e);
            return null;
        }
    }

    public ResponseEntity<ResourceRegion> streamingVideo(HttpHeaders headers, Long videoId) {
        try {
            VideoPost videoPost = getVideoPost(videoId);
            Resource resource = new FileSystemResource(videoUrl + videoPost.getVideoPath());
            long chunkSize = 1024 * 1024;
            long contentLength = resource.contentLength();
            ResourceRegion region;
            try {
                HttpRange httpRange;
                if (headers.getRange().stream().findFirst().isPresent()){
                    httpRange = headers.getRange().stream().findFirst().get();
                    long start = httpRange.getRangeStart(contentLength);
                    long end = httpRange.getRangeEnd(contentLength);
                    long rangeLength = Long.min(chunkSize, end - start + 1);

                    region = new ResourceRegion(resource, start, rangeLength);
                }else {
                    region = new ResourceRegion(resource, 0, Long.min(chunkSize, resource.contentLength()));
                }
            }
            catch (Exception e) {
                long rangeLength = Long.min(chunkSize, resource.contentLength());
                region = new ResourceRegion(resource, 0, rangeLength);
            }
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                    .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                    .body(region);
        } catch (IOException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public VideoPost getVideoPost(Long id) {
        return jpaVideoPostsRepository.findById(id).orElse(null);
    }

    public Page<VideoPost> getReelsList(Long userId, int pageNum, int pageSize){
        try {
            List<VideoPost> videoListByUserId = jpaVideoPostsRepository.findAllByUserId(userId, Sort.by(Sort.Order.desc("id")));
            Pageable pageable = PageRequest.of(pageNum, pageSize);

            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), videoListByUserId.size());
            List<VideoPost> videoPostList = videoListByUserId.subList(start, end);

            return new PageImpl<>(videoPostList, pageable, videoPostList.size());
        }
        catch (Exception e){
            log.info("reels error");
            return null;
        }
    }

    public boolean updateCaption(Long videoId, String caption){
        Optional<VideoPost> byId = jpaVideoPostsRepository.findById(videoId);
        if (byId.isPresent()) {
            VideoPost videoPost = byId.get();
            Long userIdOnSession = userService.getUserIdOnSession();
            if (userIdOnSession.equals(videoPost.getUserId())) {
                videoPost.setCaption(caption);
                jpaVideoPostsRepository.save(videoPost);
                return true;
            }
        }
        return false;
    }

    public boolean deleteReels(Long videoId) {
        Optional<VideoPost> byId = jpaVideoPostsRepository.findById(videoId);
        if (byId.isPresent()) {
            VideoPost videoPost = byId.get();
            Long userIdOnSession = userService.getUserIdOnSession();
            if (userIdOnSession.equals(videoPost.getUserId())) {
                Path path = Paths.get(videoUrl + videoPost.getVideoPath());
                if (Files.exists(path)) {
                    try {
                        Files.delete(path);
                        Files.delete(Paths.get(videoUrl+"/"+videoPost.getId()));
                        jpaVideoPostsRepository.delete(videoPost);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
