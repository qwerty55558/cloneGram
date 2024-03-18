package com.jsy.clonegram.controller;

import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.UserCreateDto;
import com.jsy.clonegram.repository.MariadbRepository;
import com.jsy.clonegram.service.EmailService;
import com.jsy.clonegram.service.RedisService;
import com.jsy.clonegram.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

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
    private final RedisService redisService;

    @GetMapping("login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login/login";
    }

    @GetMapping("sign")
    public String signPage(Model model) {
        model.addAttribute("user", new UserCreateDto());
        return "sign/sign";
    }

    @PostMapping("sign")
    public String createUser(@Valid @ModelAttribute(value = "user") UserCreateDto user, BindingResult br, Model model) {

        Map<String, String> errors = new HashMap<>();

        if (redisService.getEmailAuthStatus(user.getEmail()) == null) {
            errors.put("EmailAuthError", "이메일 인증 오류입니다.");
        }

        if (!redisService.getDuplicationCheckId(user.getUserName()).equals("success")) {
            errors.put("IdAuthError", "Id 중복 에러입니다.");
        }

        if (br.hasErrors()) {
//            log.info("error = {}", br);
            return "sign/sign";
        }
        if (!errors.isEmpty()) {
            model.addAttribute("error", errors);
            return "sign/sign";
        }


        Boolean availableUserCheck = service.createUser(user);

        if (availableUserCheck) {
            return "redirect:/login";
        }else {
            errors.put("IdAuthError", "Id 중복 에러입니다.");
            model.addAttribute("error",errors);
            return "sign/sign";
        }
    }

    @GetMapping("logout")
    public String logout() {
        log.info("로그아웃 완료");
        return "home";
    }
}
