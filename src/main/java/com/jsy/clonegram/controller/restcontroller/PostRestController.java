package com.jsy.clonegram.controller.restcontroller;

import com.jsy.clonegram.dao.Post;
import com.jsy.clonegram.dto.HomePostDto;
import com.jsy.clonegram.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @GetMapping("find/posts")
    public List<Post> getRandPost() {
        return postService.randPost(12);
    }

    @PostMapping("find/posts")
    public Page<Post> getCondPost(@RequestParam("cond") String cond, @RequestParam("pageNum") Integer pageNum) {
        return postService.getCondPost(cond, pageNum, 12);
    }

    @PostMapping("find/likey/info")
    public boolean getLikeyStat(@RequestParam("postname") Long postName) {
        return postService.getLikeyStat(postName);
    }

    @PostMapping("find/likey")
    public String setLikey(@RequestParam("postname") Long postName, @RequestParam("action") String req) {
        return postService.setLikey(postName, req);
    }

    @PostMapping("find/posts/recommended")
    public Page<HomePostDto> getRecommendedPost(@RequestParam("recommendedPageNum") Integer pageNum) {
        return postService.getRecommendedPosts(12, pageNum);
    }

    @PostMapping("delete/post")
    public boolean deletePost(@RequestParam("postid") Long postid) {
        log.info("postid = {}", postid);
        return postService.deletePost(postid);
    }

    @PostMapping("update/post")
    public boolean updatePost(@RequestParam("postid") Long postid, @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "caption", required = false) String caption) {
        log.info("postid = {}, file = {}, caption = {}", postid, file, caption);
        return postService.updatePost(postid, file, caption);
    }

}
