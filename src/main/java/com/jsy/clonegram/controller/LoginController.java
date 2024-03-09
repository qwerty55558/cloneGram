package com.jsy.clonegram.controller;

import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.repository.MariadbRepository;
import com.jsy.clonegram.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final UserService service;

    @GetMapping("login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login/login";
    }

    @GetMapping("sign")
    public String signPage(Model model) {
        model.addAttribute("user", new User());
        return "sign/sign";
    }

    @PostMapping("sign")
    public String createUser(@ModelAttribute(value = "user") User user) {
        service.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("logout")
    public String logout() {
        log.info("로그아웃 완료");
        return "home";
    }
}
