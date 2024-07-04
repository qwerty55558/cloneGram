package com.jsy.clonegram.controller;

import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * 단순하게 name을 받아와 화면에 뿌릴 데이터를 model에 보내는 컨드롤러
 */

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model){

        User userBySession = userService.getUserBySession();
        model.addAttribute("user", userBySession);
        
        return "dashboard/dashboard";
    }
}
