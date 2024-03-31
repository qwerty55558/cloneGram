package com.jsy.clonegram.controller;

import com.jsy.clonegram.dao.Comment;
import com.jsy.clonegram.dao.Post;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.repository.JpaCommentRepository;
import com.jsy.clonegram.repository.PostRepository;
import com.jsy.clonegram.repository.UserRepository;
import com.jsy.clonegram.service.UserPictureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Stream;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final UserPictureService pictureService;
    private final UserRepository userRepository;
    private final JpaCommentRepository commentRepository;

    @GetMapping("post")
    public String redirectPost(@RequestParam(name = "id") Long id, Model model) {
        Map<Long, String> picMap = new HashMap<>(); // 이미지 Map
        Map<Long, String> usernameMap = new HashMap<>(); // userName Map

        Optional<Post> postById = postRepository.findById(id); // 해당 id 의 Post 를 불러옴.

        if (postById.isPresent()) { // post 가 존재하면
            model.addAttribute("post", postById.get()); // post 데이터를 보냄
            picMap.put(postById.get().getUserId(), pictureService.getMidSizeProfileById(postById.get().getUserId())); // 작성자의 프로필 사진을 Map 에 등록
            usernameMap.put(postById.get().getUserId(), userRepository.findById(postById.get().getUserId()).get().getUserName()); // 작성자의 이름을 Map 에 등록

            List<Comment> commentsByPostId = commentRepository.findCommentsByPostId(postById.get().getId()); // post 의 comments 를 List 로 불러옴
            if (!commentsByPostId.isEmpty()) { // 댓글이 존재하면
                List<Long> picIdList = commentsByPostId.stream().map(Comment::getUserId).distinct().toList(); // 중복을 제거한 userId 리스트를 stream 으로 만듦
                picIdList.stream().distinct().forEach(c -> {
                    picMap.put(c, pictureService.getMidSizeProfileById(c));
                    usernameMap.put(c, userRepository.findById(c).get().getUserName());
                }); // 중복이 없는 리스트에서 한 번씩 Map 에 프로필 사진과 userName 을 등록해줌
                model.addAttribute("comments", commentsByPostId); // 코멘트들은 댓글이 존재할 때만 model 에 등록
            }
            log.info("usernameMap = {}",usernameMap);


            model.addAttribute("picMap", picMap); // 모델에 사진 맵과 유저네임 맵을 등록
            model.addAttribute("usernameMap", usernameMap);
            return "post/post";

        }
        return "home";
    }

}
