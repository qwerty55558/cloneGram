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

/**
 * getmapping login, sign은 model에 빈 user객체를 넣어서 thymeleaf에서 편하게 자동완성 사용
 * postsign은 ID, password, email을 받아와서 notnull인 부분만 채워 User를 만듦
 * logout getmapping은 Security 체크를 위해 넣어봄 (필터를 베이스로 구현된 security는 logout으로 들어온 get 요청을 필터링하여 자신이 처리함)
 */
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
