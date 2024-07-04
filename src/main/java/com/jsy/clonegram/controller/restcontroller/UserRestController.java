package com.jsy.clonegram.controller.restcontroller;

import com.jsy.clonegram.dto.UserProfileDto;
import com.jsy.clonegram.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserRestController {

    private final FollowService followService;

    @PostMapping("/follow")
    public String followUser(@RequestParam(name = "followuser") Long id) {
        return followService.followUser(id);
    }

    @PostMapping("/unfollow")
    public String unfollowUser(@RequestParam(name = "unfollowuser") Long id) {
        return followService.unfollowUser(id);
    }

    @GetMapping("/find/myfollower")
    public List<UserProfileDto> getMyFollower() {
        return followService.getMyFollower();
    }

    @GetMapping("/find/myfollowing")
    public List<UserProfileDto> getMyFollowing() {
        return followService.getMyFollowing();
    }
}
