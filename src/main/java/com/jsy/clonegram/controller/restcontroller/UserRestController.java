package com.jsy.clonegram.controller.restcontroller;

import com.jsy.clonegram.dao.Follow;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.UserProfileDto;
import com.jsy.clonegram.repository.UserRepository;
import com.jsy.clonegram.service.FollowService;
import com.jsy.clonegram.service.UserPictureService;
import com.jsy.clonegram.service.UserService;
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
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserPictureService userPictureService;

    @PostMapping("/follow")
    public String followUser(@RequestParam(name = "followuser") Long id) {
//        log.info("id = {}",id);
        Long userIdOnSession = userService.getUserIdOnSession();
        Optional<Follow> first = userRepository.findFollowingsByUserId(userIdOnSession).stream().filter(c -> c.getUserId().equals(id)).findFirst();
        if (first.isEmpty()) {
            followService.followUser(userIdOnSession, id);
        }
        return "success";
    }

    @PostMapping("/unfollow")
    public String unfollowUser(@RequestParam(name = "unfollowuser") Long id) {
        Long userIdOnSession = userService.getUserIdOnSession();
        followService.unfollowUser(userIdOnSession, id);
        return "success";
    }

    @GetMapping("/find/myfollower")
    public List<UserProfileDto> getMyFollower() {

        LinkedList<UserProfileDto> userProfileDtos = new LinkedList<>();


        Long userIdOnSession = userService.getUserIdOnSession();
        userRepository.findFollowersByUserId(userIdOnSession).stream().distinct().map(c -> c.getFollowerId()).forEach(c -> {
            Optional<User> byId = userRepository.findById(c);
            if (byId.isPresent()) {
                UserProfileDto userProfileDto = new UserProfileDto();
                userProfileDto.setUserName(byId.get().getUserName());
                userProfileDto.setUserPic(byId.get().getProfileImageUrl());
                userProfileDto.setUserId(byId.get().getId());
                userProfileDtos.add(userProfileDto);
            }
        });

        return userProfileDtos;
    }

    @GetMapping("/find/myfollowing")
    public List<UserProfileDto> getMyFollowing(){
        LinkedList<UserProfileDto> userProfileDtos = new LinkedList<>();


        Long userIdOnSession = userService.getUserIdOnSession();
        userRepository.findFollowingsByUserId(userIdOnSession).stream().distinct().map(c -> c.getUserId()).forEach(c -> {
            Optional<User> byId = userRepository.findById(c);
            if (byId.isPresent()){
                UserProfileDto userProfileDto = new UserProfileDto();
                userProfileDto.setUserName(byId.get().getUserName());
                userProfileDto.setUserPic(byId.get().getProfileImageUrl());
                userProfileDto.setUserId(byId.get().getId());
                userProfileDtos.add(userProfileDto);
            }
        });

        return userProfileDtos;
    }
}
