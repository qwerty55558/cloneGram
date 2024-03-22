package com.jsy.clonegram.controller;

import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.PicUrlDto;
import com.jsy.clonegram.repository.UserRepository;
import com.jsy.clonegram.service.UserPictureService;
import com.jsy.clonegram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserRepository rep;
    private final UserService service;
    private final UserPictureService pic;


    @GetMapping("/profile/edit")
    public String editProfile(Model model) {

        String usernameOnSession = service.getUsernameOnSession();
        Optional<User> byName = rep.findByName(usernameOnSession);

        PicUrlDto picUrlDto = new PicUrlDto();

        picUrlDto.setProfilePic(pic.getProfilePicUrl());
        picUrlDto.setMiniPic(pic.getMiniPicUrl());


        if (byName.isPresent()) {
            model.addAttribute("user", byName.get());
            model.addAttribute("picurl", picUrlDto);

            return "user/editprofile";
        }

        return "redirect:/login";
    }
}
