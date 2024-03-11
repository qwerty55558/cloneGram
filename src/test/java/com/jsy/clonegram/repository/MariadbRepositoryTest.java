package com.jsy.clonegram.repository;

import com.jsy.clonegram.config.UserConfig;
import com.jsy.clonegram.dao.Grade;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.UserUpdateDto;
import com.jsy.clonegram.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

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
        User user = new User();
        user.setUserName("wer");
        user.setPassword("1234");
        user.setEmail("123@gmail.com");
        user.setBio("와우");
        user.setGrade(Grade.Admin);
        user.setFullName("뽀뽀봉");
        user.setProfileImageUrl("LINK:ERoiqwejuroiqujroilr");
        service.createUser(user);
        log.info("user = {}",user);

        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setGrade(Grade.Bronze);
        userUpdateDto.setEmail("qwer@eee");
        userUpdateDto.setPassword("비번입니다.");
        Optional<User> byName = repository.findByName(user.getUserName());
        service.updateUser(byName.get(), userUpdateDto);
        log.info("DTO = {}", userUpdateDto);

        List<User> all = repository.findAll();
        log.info("all = {}",all);
        User update = repository.findByName(user.getUserName()).get();

        log.info("update = {}", update);

    }
}