package com.jsy.clonegram.service;

import com.jsy.clonegram.dao.EmitterType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class SseEmitterService {

    private final UserService userService;

    private final Map<String, org.springframework.web.servlet.mvc.method.annotation.SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter newSseEmitter(Long userid, String type) {
        SseEmitter emitter = new SseEmitter();

        emitter.onCompletion(() -> emitters.remove(userid + "_" + type));
        emitter.onTimeout(() -> emitters.remove(userid + "_" + type));
        emitter.onError(throwable -> emitters.remove(userid + "_" + type));

        emitters.put(userid + "_" + type, emitter);

        log.info("emitters = {}", emitters);

        return emitter;
    }

    public SseEmitter newSseEmitter(String type) {
        return newSseEmitter(userService.getUserIdOnSession(), type);
    }

    public SseEmitter getMessagesEmitter(Long targetId) {
        return emitters.get(targetId + "_" + "messages");
    }

    public SseEmitter getMessagesEmitter() {
        return emitters.get(userService.getUserIdOnSession() + "_" + EmitterType.MESSAGE.getType());
    }

    public SseEmitter getNotificationsEmitter(Long targetId) {
        return emitters.get(targetId + "_" + "notifications");
    }

    public SseEmitter getNotificationsEmitter() {
        return emitters.get(userService.getUserIdOnSession() + "_" + EmitterType.NOTIFICATION.getType());
    }

    public void deleteMessagesEmitter() {
        SseEmitter sseEmitter = emitters.get(userService.getUserIdOnSession() + "_" + EmitterType.MESSAGE.getType());
        sseEmitter.complete();
        log.info("sseEmitterdeleteMsg = {}", sseEmitter);
    }

    public void deleteNotificationsEmitter() {
        SseEmitter sseEmitter = emitters.get(userService.getUserIdOnSession() + "_" + EmitterType.NOTIFICATION.getType());
        sseEmitter.complete();
        log.info("sseEmitterdeleteNotify = {}", sseEmitter);
    }

    public void deleteAllEmitter(Long userId) {
        if (getMessagesEmitter(userId) != null || getNotificationsEmitter(userId) != null) {
            getNotificationsEmitter(userId).complete();
            getMessagesEmitter(userId).complete();
        }
    }
}
