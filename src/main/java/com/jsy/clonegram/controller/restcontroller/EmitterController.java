package com.jsy.clonegram.controller.restcontroller;

import com.jsy.clonegram.service.SseEmitterService;
import com.jsy.clonegram.service.UserService;
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
    private final UserService userService;

    @GetMapping("/find/emitter")
    public SseEmitter getEmitter(){
        return sseEmitterService.getEmitter(userService.getUserIdOnSession());
    }
}
