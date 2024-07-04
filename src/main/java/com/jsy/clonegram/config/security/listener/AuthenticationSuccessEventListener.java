package com.jsy.clonegram.config.security.listener;

import com.jsy.clonegram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
//        UserDetails principal = (UserDetails) event.getAuthentication().getPrincipal();
//        Long userIdByName = userService.getUserIdByName(principal.getUsername());
//        SseEmitter notificationsEmitter = emitterService.getNotificationsEmitter(userIdByName);
//        if (notificationsEmitter != null) {
//            notificationsEmitter.complete(); // 기존 연결 종료
//        }
//        emitterService.newSseEmitter(userIdByName, EmitterType.NOTIFICATION.getType()); // 새로운 연결 생성
    }
}
