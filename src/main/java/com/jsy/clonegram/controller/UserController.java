package com.jsy.clonegram.controller;

import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.FollowSetDto;
import com.jsy.clonegram.dto.PicUrlDto;
import com.jsy.clonegram.dto.UserEditDto;
import com.jsy.clonegram.dto.UserUpdateDto;
import com.jsy.clonegram.repository.PostRepository;
import com.jsy.clonegram.repository.UserRepository;
import com.jsy.clonegram.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserPictureService pictureService;
    private final FollowService followService;
    private final PostService postService;

    @GetMapping("/profile/edit")
    public String editProfile(Model model) {
        User userBySession = userService.getUserBySession();
        UserEditDto userEditDto = new UserEditDto();
        userEditDto.setBio(userBySession.getBio());
        userEditDto.setFullName(userBySession.getFullName());

        model.addAttribute("user", userBySession);
        model.addAttribute("picurl", pictureService.getMidSizeProfileById(userBySession.getId()));
        model.addAttribute("userEditDto", userEditDto);

        return "user/editprofile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@ModelAttribute("user")UserEditDto user) {
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        if (user.getBio() != null && user.getBio().length() < 255) {
            userUpdateDto.setBio(user.getBio());
        }
//        if (user.getFullName() != null && user.getFullName().length() >= 3) {
//            userUpdateDto.setFullName(user.getFullName());
//        }

        if (user.getFullName().length() < 20) {
            userUpdateDto.setFullName(user.getFullName());
        }


        if (user.getPassword() != null && user.getPassword().length() < 255 && !user.getPassword().isEmpty()) {
            userUpdateDto.setPassword(userService.encodePassword(user.getPassword()));
        }
        userService.updateUser(userService.getUserIdOnSession(), userUpdateDto);
        return "redirect:/?tab=profile";
    }

    @GetMapping("/profile")
    public String userProfile(@RequestParam(name = "id") String userid, Model model) {
        Long id = 0L;
        id = userService.getUserIdByName(userid);
        if (id == null || id == 0) {
            id = Long.parseLong(userid);
        }


        Long sessionId = userService.getUserIdOnSession();

        if (id.equals(sessionId)) {
            return "redirect:/home?tab=profile";
        }

        PicUrlDto picUrlDto = new PicUrlDto();
        picUrlDto.setMiniPic(pictureService.getMiniPicUrl(userService.getUserNameByUserId(id)));
        picUrlDto.setProfilePic(pictureService.getProfilePicUrlHighQ(userService.getUserNameByUserId(id)));

        FollowSetDto followSetDto = new FollowSetDto();

        followSetDto.setFollower(followService.getFollowerCount(id));
        followSetDto.setFollowing(followService.getFollowingCount(id));
        followSetDto.setFollowCheck(followService.checkFollowStatus(sessionId,id));

        model.addAttribute("follow", followSetDto);
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("picurl", picUrlDto);
        model.addAttribute("posts", postService.getPostsByUserId(id));
        return "user/userprofile";
    }

    @GetMapping("/reels")
    public String reelsPage(){
        return "post/reels";
    }

    @GetMapping("/myreels")
    public String myReelsPage(){
        return "user/editreels";
    }
}
