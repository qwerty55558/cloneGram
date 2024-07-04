package com.jsy.clonegram.controller.restcontroller;

import com.jsy.clonegram.dao.EmitterType;
import com.jsy.clonegram.service.SseEmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@Slf4j
@RequiredArgsConstructor
public class EmitterController {
    private final SseEmitterService sseEmitterService;

    @GetMapping(value = "/find/emitter/notifications")
    public SseEmitter getNotifications(){
        SseEmitter notificationsEmitter = sseEmitterService.getNotificationsEmitter();
        if (notificationsEmitter != null) {
            return notificationsEmitter;
        }else {
            return sseEmitterService.newSseEmitter(EmitterType.NOTIFICATION.getType());
        }
    }

    @GetMapping("/find/emitter/messages")
    public SseEmitter getMessage(){
        if (sseEmitterService.getMessagesEmitter() != null) {
            return sseEmitterService.getMessagesEmitter();
        }
        return null;
    }

    @GetMapping("/delete/emitter/messages")
    public void deleteMessage(){
        log.info("deleteMessages");
        sseEmitterService.deleteMessagesEmitter();
    }
}
