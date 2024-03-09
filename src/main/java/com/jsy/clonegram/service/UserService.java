package com.jsy.clonegram.service;

import com.jsy.clonegram.config.security.SecurityConfig;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        user.setEmail(user.getEmail());

        rep.save(user);

        return true;
    }
}
