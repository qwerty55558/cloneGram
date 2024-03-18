package com.jsy.clonegram.service;

import com.jsy.clonegram.dao.Grade;
import com.jsy.clonegram.dto.UserCreateDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserServiceTest {

    @Autowired
    UserService service;

    @Test
    void createUser() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setUserName("qwer");
        userCreateDto.setEmail("qwer@qwer");
        userCreateDto.setPassword("1234");
        userCreateDto.setGrade(Grade.Bronze);
        Boolean user = service.createUser(userCreateDto);

        log.info("user = {}", user);

    }
}