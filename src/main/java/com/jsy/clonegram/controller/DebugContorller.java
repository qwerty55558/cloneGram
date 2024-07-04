package com.jsy.clonegram.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
@Slf4j
public class DebugContorller {
    @GetMapping("/hello")
    public void hello(Locale locale) {
        log.info("Current Locale : {}", locale);
    }
}
