package com.jsy.clonegram.service;

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

    private final Map<Long, org.springframework.web.servlet.mvc.method.annotation.SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter newEmitter() {
        Long userIdOnSession = userService.getUserIdOnSession();
        return getSseEmitter(userIdOnSession);
    }

    public SseEmitter newEmitter(Long userid){
        return getSseEmitter(userid);
    }

    private SseEmitter getSseEmitter(Long userid) {
        SseEmitter emitter = new SseEmitter(0L);

        emitters.put(userid, emitter);

        emitter.onCompletion(() -> emitters.remove(userid));

//        log.info(emitters.toString());
        return emitter;
    }

    public SseEmitter getEmitter(Long userId) {
//        log.info(emitters.toString());
        return emitters.get(userId);
    }

    public Boolean isEmitterActive(Long userId) {
//        log.info(emitters.toString());
        return emitters.containsKey(userId);
    }

    public void removeEmitter(Long userId) {
        emitters.remove(userId);
//        log.info(emitters.toString());
    }
}
