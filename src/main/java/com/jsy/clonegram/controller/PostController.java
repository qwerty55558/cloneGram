package com.jsy.clonegram.controller;

import com.jsy.clonegram.dao.Post;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.repository.PostRepository;
import com.jsy.clonegram.repository.UserRepository;
import com.jsy.clonegram.service.PostService;
import com.jsy.clonegram.service.UserPictureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final UserPictureService pictureService;
    private final UserRepository userRepository;

    @GetMapping("post")
    public String redirectPost(@RequestParam(name = "id") Long id, Model model) {

        Optional<Post> byId = postRepository.findById(id);
        String profileById = pictureService.getProfileById(byId.get().getUserId());
        Optional<User> byId1 = userRepository.findById(byId.get().getUserId());

        if (byId.isPresent()) {
            model.addAttribute("postcreator", byId1.get().getUserName());
            model.addAttribute("propic", profileById);
            model.addAttribute("post", byId.get());
            return "post/post";
        }

        return "home";
    }

}
