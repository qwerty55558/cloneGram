package com.jsy.clonegram.service;

import com.jsy.clonegram.dao.Grade;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.UserCreateDto;
import com.jsy.clonegram.dto.UserUpdateDto;
import com.jsy.clonegram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

/**
 * User의 비즈니스 로직을 담음
 * createUser 는 user의 password의 encoding 과정이 필요하여 encode를 비밀번호로 만듦.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository rep;

    @Autowired
    PasswordEncoder encoder;


    public Boolean createUser(UserCreateDto user){

        Optional<User> byName = rep.findByName(user.getUserName());
        if (byName.isEmpty()) {
            String encode = encoder.encode(user.getPassword());
            user.setPassword(encode);
            user.setGrade(Grade.Bronze);

            rep.save(user);

            return true;
        }

        return false;
    }

    public Boolean updateUser(User user, UserUpdateDto dto) {
        dto.setTargetId(user.getId());
        rep.update(dto);
        return true;
    }

    public String getUsernameOnSession(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUsername();
    }
}
