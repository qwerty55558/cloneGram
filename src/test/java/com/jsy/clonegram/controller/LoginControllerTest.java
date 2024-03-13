package com.jsy.clonegram.controller;

import com.jsy.clonegram.dto.UserCreateDto;
import com.jsy.clonegram.service.RedisService;
import com.jsy.clonegram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class LoginControllerTest {

    @Autowired
    RedisService service;
    @Autowired
    UserService userService;

    @Test
    void checkDuplication() {
        service.setDuplicationCheckId("qwer");
        String duplicationCheckId = service.getDuplicationCheckId("qwer");
        log.info("duplication = {}",duplicationCheckId);
    }

    @Test
    void createuser(){
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setUserName("qwer");
        userCreateDto.setEmail("asdf@asdf.com");
        userCreateDto.setPassword("1234");
        userService.createUser(userCreateDto);
    }
}