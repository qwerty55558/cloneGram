package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Grade;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.UserCreateDto;
import com.jsy.clonegram.dto.UserUpdateDto;
import com.jsy.clonegram.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Slf4j
class MariadbRepositoryTest {

    @Autowired
    UserRepository repository;
    @Autowired
    UserService service;



    @Test
    void testUpdate(){
        UserCreateDto user = new UserCreateDto();
        user.setUserName("wer");
        user.setPassword("");
        user.setEmail("123");
        service.createUser(user);
        log.info("user = {}",user);

        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setGrade(Grade.Admin);
        userUpdateDto.setEmail("qwer@eee");
        userUpdateDto.setPassword("비번입니다.");
        userUpdateDto.setBio("와우 대박");
        userUpdateDto.setFullName("양금모");
        userUpdateDto.setProfileImageUrl("대박사건스");
        Optional<User> byName = repository.findByName(user.getUserName());
        service.updateUser(byName.get(), userUpdateDto);
        log.info("DTO = {}", userUpdateDto);

        List<User> all = repository.findAll();
        log.info("all = {}",all);
        User update = repository.findByName(user.getUserName()).get();

        log.info("update = {}", update);

    }
}