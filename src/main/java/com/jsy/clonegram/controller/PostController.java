package com.jsy.clonegram.controller;

import com.jsy.clonegram.dao.Comment;
import com.jsy.clonegram.dao.Constants;
import com.jsy.clonegram.dao.Post;
import com.jsy.clonegram.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final UserPictureService pictureService;
    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;
    private final LikeyService likeyService;

    @Value("${POST.CAPTIONSIZE}")
    private Integer captionSize;

    @Value("${FILE.MAXSIZE}")
    private Integer fileMaxSize;

    @GetMapping("post")
    public String redirectPost(@RequestParam(name = "id") Long id, Model model) {

        Post post = postService.getPost(id);
        if (post != null) {
            model.addAttribute("post", post);
            Map<Long, String> usernameMap = postService.getUsernameByPost(post);
            Map<Long, String> picMap = postService.getPicByPost(post);

            List<Comment> commentsByPostId = commentService.getCommentsByPostId(post.getId());
            if (!commentsByPostId.isEmpty()) {
                List<Long> picIdList = commentsByPostId.stream().map(Comment::getUserId).distinct().toList(); // 중복을 제거한 userId 리스트를 stream 으로 만듦
                picIdList.stream().distinct().forEach(c -> {
                    picMap.put(c, pictureService.getMidSizeProfileById(c));
                    usernameMap.put(c, userService.getUserNameByUserId(c));
                }); // 중복이 없는 리스트에서 한 번씩 Map 에 프로필 사진과 userName 을 등록해줌
                model.addAttribute("comments", commentsByPostId); // 코멘트들은 댓글이 존재할 때만 model 에 등록
            }

            Optional<Long> likeyByPostId = likeyService.getLikeyByPostId(post.getId());
            likeyByPostId.ifPresent(c -> model.addAttribute("likeyCount", c));

            model.addAttribute("picMap", picMap); // 모델에 사진 맵과 유저네임 맵을 등록
            model.addAttribute("usernameMap", usernameMap);
            if (post.getUserId().equals(userService.getUserIdOnSession())) {
                model.addAttribute("mine", true);
            } else {
                model.addAttribute("mine", false);
            }

            model.addAttribute("fileMaxSize", fileMaxSize * Constants.FILE_MAX_SIZE);
            model.addAttribute("postCaptionSize", captionSize);
            return "post/post";
        }
        return "home";
    }

}
