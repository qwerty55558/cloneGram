package com.jsy.clonegram.config.mybatis;

import com.jsy.clonegram.mybatis.mapper.UserMapper;
import com.jsy.clonegram.repository.MariadbRepository;
import com.jsy.clonegram.repository.UserRepository;
import com.jsy.clonegram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {

    private final UserMapper userMapper;

    @Bean
    public UserService userService(){
        return new UserService(userRepository());
    }

    @Bean
    public UserRepository userRepository(){
        return new MariadbRepository(userMapper);
    }
}
