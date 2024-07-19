package com.jsy.clonegram.controller;

import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.UserCreateDto;
import com.jsy.clonegram.service.EmailService;
import com.jsy.clonegram.service.LoginService;
import com.jsy.clonegram.service.UserService;
import com.jsy.clonegram.service.ValidationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Locale;
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

    private final LoginService loginService;
    private final UserService userService;
    private final ValidationService validationService;
    private final EmailService emailService;
    private final MessageSource messageSource;

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
    public String createUser(@Valid @ModelAttribute(value = "user") UserCreateDto user, BindingResult br, Model model, HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        log.info("user = {}", user);

        if (!loginService.getEmailAuthStatus(user.getEmail())) {
            errors.put("EmailAuthError", messageSource.getMessage("NotAuth.user.Email",null,null, request.getLocale()));
        }

        if (!loginService.getDuplicationCheckId(user.getUserName())) {
            errors.put("IdAuthError", messageSource.getMessage("NotAuth.user.userName",null,null, request.getLocale()));
        }

        if (br.hasErrors()) {
            return "sign/sign";
        }
        if (!errors.isEmpty()) {
            model.addAttribute("error", errors);
            return "sign/sign";
        }


        Boolean availableUserCheck = userService.createUser(user);

        if (availableUserCheck) {
            loginService.deleteAuthEmail(user.getEmail());
            return "redirect:/login";
        } else {
            errors.put("IdAuthError", messageSource.getMessage("NotAuth.user.userName",null,null, request.getLocale()));
            model.addAttribute("error", errors);
            return "sign/sign";
        }
    }

    // Spring Security Chain Filter 에 걸러져 엔드포인트에 도달하지 못하도록 설계
    @GetMapping("logout")
    public String logout() {
        log.info("로그아웃 완료");
        return "home";
    }

    @GetMapping("find/password")
    public String findPassword() {
        return "user/findpassword";
    }

    @PostMapping("find/password")
    public String findPasswordRequest(@RequestParam("email") String email, Model model, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        if (validationService.getValidateEmail(email)) {
            errors.put("EmailAuthError", messageSource.getMessage("Check.Email", null, null, request.getLocale()));
        }
        if (!errors.isEmpty()) {
            model.addAttribute("error", errors);
            return "user/findpassword";
        }
        
        // 임시 비밀번호 설정과 이메일 발송

        emailService.sendResetPasswordEmail(email, request.getLocale());

        model.addAttribute("pwmsg", messageSource.getMessage("user.pw.changed", null, null, request.getLocale()));
        
        return "login/login";
    }

}
