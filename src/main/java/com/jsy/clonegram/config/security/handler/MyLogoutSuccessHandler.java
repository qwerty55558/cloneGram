package com.jsy.clonegram.config.security.handler;

import com.jsy.clonegram.service.SseEmitterService;
import com.jsy.clonegram.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    private final SseEmitterService sseEmitterService;
    private final UserService userService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
//        log.info(principal.getUsername());
        sseEmitterService.removeEmitter(userService.getUserIdByName(principal.getUsername()) );
        response.sendRedirect("/login");
    }
}
