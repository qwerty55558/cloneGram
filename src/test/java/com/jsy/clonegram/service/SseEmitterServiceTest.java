package com.jsy.clonegram.service;

import com.jsy.clonegram.dao.EmitterType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@SpringBootTest
@Slf4j
class SseEmitterServiceTest {

    @Autowired
    private SseEmitterService sseEmitterService;

    @Test
    void testEmitter(){
        SseEmitter sseEmitter = sseEmitterService.newSseEmitter(1L, EmitterType.NOTIFICATION.getType());
        log.info("emitter = {}", sseEmitter.toString());
        SseEmitter notificationsEmitter = sseEmitterService.getNotificationsEmitter(2L);
        if (notificationsEmitter == null) {
            log.info("null");
        }else {
            log.info("notificationsEmitter = {}", notificationsEmitter.toString());
        }
    }
}