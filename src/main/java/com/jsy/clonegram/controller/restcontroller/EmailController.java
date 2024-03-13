package com.jsy.clonegram.controller.restcontroller;

import com.jsy.clonegram.dto.CodeRequestDto;
import com.jsy.clonegram.dto.EmailRequestDto;
import com.jsy.clonegram.dto.DefaultMessageDto;
import com.jsy.clonegram.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final EmailService service;


    @PostMapping("/sendEmail")
    public DefaultMessageDto sendEmail(@RequestBody EmailRequestDto req) {
        try {
            service.sendAuthEmail(req.getEmail());
            return new DefaultMessageDto("success");
        } catch (Exception e) {
            return new DefaultMessageDto("fail");
        }
    }

    @PostMapping("/checkCode")
    public DefaultMessageDto checkCode(@RequestBody CodeRequestDto req) {
        try {
            Boolean b = service.checkCode(req.getEmail(), req.getCode());
            if (b) {
                return new DefaultMessageDto("success");
            }else {
                return new DefaultMessageDto("fail");
            }
        } catch (Exception e) {
            return new DefaultMessageDto("fail");
        }
    }
}
