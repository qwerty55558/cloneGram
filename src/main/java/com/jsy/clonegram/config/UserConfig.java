package com.jsy.clonegram.config;

import com.jsy.clonegram.mybatis.mapper.UserMapper;
import com.jsy.clonegram.repository.MariadbRepository;
import com.jsy.clonegram.repository.UserRepository;
import com.jsy.clonegram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User 단에서 사용되는 클래스들을 Bean 등록 시킴
 */
@Configuration
@RequiredArgsConstructor
public class UserConfig {

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
