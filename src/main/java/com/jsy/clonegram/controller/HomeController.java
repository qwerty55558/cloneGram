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

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final UserService service;
    private final UserRepository rep;

    @GetMapping({"/","/home"})
    public String homeController(Model model) {
        UserDetails principal1 = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal1.getUsername();
//        String password = principal1.getPassword();

        Optional<User> byName = rep.findByName(username);

        model.addAttribute("user", byName.get());
        return "home";
    }
}
