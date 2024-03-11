package com.jsy.clonegram.controller;

import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

/**
 * 단순하게 name을 받아와 화면에 뿌릴 데이터를 model에 보내는 컨드롤러
 */

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository rep;

    @GetMapping("/dashboard")
    public String dashboard(Model model){

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();

        Optional<User> byName = rep.findByName(username);

        model.addAttribute("user", byName.get());
        return "dashboard/dashboard";
    }
}
