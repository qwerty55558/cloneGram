package com.jsy.clonegram.config.security.listener;

import com.jsy.clonegram.service.SseEmitterService;
import com.jsy.clonegram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class MySessionListener implements ApplicationListener<SessionDestroyedEvent> {

    private final UserService userService;
    private final SseEmitterService emitterService;

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        event.getSecurityContexts().forEach(securityContext -> {
            UserDetails principal = (UserDetails) securityContext.getAuthentication().getPrincipal();
            Long userIdByName = userService.getUserIdByName(principal.getUsername());
            emitterService.removeEmitter(userIdByName);
        });
    }
}
