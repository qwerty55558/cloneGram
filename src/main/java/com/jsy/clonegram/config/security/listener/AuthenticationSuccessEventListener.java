package com.jsy.clonegram.config.security.listener;

import com.jsy.clonegram.service.SseEmitterService;
import com.jsy.clonegram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final SseEmitterService emitterService;
    private final UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        UserDetails principal = (UserDetails) event.getAuthentication().getPrincipal();
        emitterService.newEmitter(userService.getUserIdByName(principal.getUsername()));
    }
}
