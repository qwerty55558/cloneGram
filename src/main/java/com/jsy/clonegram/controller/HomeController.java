package com.jsy.clonegram.controller;

import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.repository.UserRepository;
import com.jsy.clonegram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

/**
 * home으로 get 요청이 오면 `home` 페이지를 보여준다.
 * Securitycontextholer에서 인증정보를 불러와서 username을 받아옴 이를 추후에
 * findbyname 메소드와 연동해 다른 정보들을 출력해줄 수 있다.
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final UserService service;
    private final UserRepository rep;

    @GetMapping({"/","/home"})
    public String homeController(Model model) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();

        Optional<User> byName = rep.findByName(username);

        model.addAttribute("user", byName.get());
        return "home";
    }
}
