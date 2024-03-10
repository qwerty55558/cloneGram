package com.jsy.clonegram.service;

import com.jsy.clonegram.config.security.SecurityConfig;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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


    public Boolean createUser(User user){
        String encode = encoder.encode(user.getPassword());
        user.setPassword(encode);

        rep.save(user);

        return true;
    }
}
