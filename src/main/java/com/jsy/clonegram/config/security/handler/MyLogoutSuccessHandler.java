package com.jsy.clonegram.config.security.handler;

import com.jsy.clonegram.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    private final UserService userService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        UserDetails principal = (UserDetails) authentication.getPrincipal();
//        Long userIdByName = userService.getUserIdByName(principal.getUsername());
//        SseEmitter notificationsEmitter = sseEmitterService.getNotificationsEmitter(userIdByName);
//        log.info("userIdByName = {}" , userIdByName);
//        notificationsEmitter.complete();
        response.sendRedirect("/login");
    }
}
