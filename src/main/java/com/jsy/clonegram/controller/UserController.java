package com.jsy.clonegram.controller;

import com.jsy.clonegram.dao.Post;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.FollowSetDto;
import com.jsy.clonegram.dto.PicUrlDto;
import com.jsy.clonegram.repository.PostRepository;
import com.jsy.clonegram.repository.UserRepository;
import com.jsy.clonegram.service.FollowService;
import com.jsy.clonegram.service.UserPictureService;
import com.jsy.clonegram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserPictureService pictureService;
    private final PostRepository postRepository;
    private final FollowService followService;

    @GetMapping("/profile/edit")
    public String editProfile(Model model) {

        String usernameOnSession = userService.getUsernameOnSession();
        Optional<User> byName = userRepository.findByName(usernameOnSession);

        PicUrlDto picUrlDto = new PicUrlDto();

        picUrlDto.setProfilePic(pictureService.getProfilePicUrl());
        picUrlDto.setMiniPic(pictureService.getMiniPicUrl());


        if (byName.isPresent()) {
            model.addAttribute("user", byName.get());
            model.addAttribute("picurl", picUrlDto);

            return "user/editprofile";
        }

        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String userProfile(@RequestParam(name = "id") Long id, Model model) {
        Optional<User> byName = userRepository.findById(id);
        if (byName.isPresent()) {
            Long userIdOnSession = userService.getUserIdOnSession();

            if (byName.get().getId().equals(userIdOnSession)) {
                return "redirect:/home?tap=profile";
            }

            List<Post> postsById = postRepository.findPostsById(byName.get().getId());

            PicUrlDto picUrlDto = new PicUrlDto();
            picUrlDto.setMiniPic(pictureService.getMiniPicUrl(byName.get().getUserName()));
            picUrlDto.setProfilePic(pictureService.getProfilePicUrlHighQ(byName.get().getUserName()));

            FollowSetDto followSetDto = new FollowSetDto();

            followSetDto.setFollower(followService.getFollowerCount(byName.get().getId()));
            followSetDto.setFollowing(followService.getFollowingCount(byName.get().getId()));
            followSetDto.setFollowCheck(followService.checkFollowStatus(userIdOnSession,id));


            log.info("Follow = {}",followSetDto);

            model.addAttribute("follow", followSetDto);
            model.addAttribute("user", byName.get());
            model.addAttribute("picurl", picUrlDto);
            model.addAttribute("posts", postsById);
            return "user/userprofile";
        }

        return "redirect:/login";
    }
}
