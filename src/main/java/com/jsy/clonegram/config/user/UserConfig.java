package com.jsy.clonegram.config.user;

import com.cloudinary.Cloudinary;
import com.jsy.clonegram.mybatis.mapper.UserMapper;
import com.jsy.clonegram.repository.MariadbRepository;
import com.jsy.clonegram.repository.UserRepository;
import com.jsy.clonegram.service.EmailService;
import com.jsy.clonegram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * User 단에서 사용되는 클래스들을 Bean 등록 시킴
 */
@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class UserConfig {

    @Value("${CLOUDINARY.URL}")
    private String cloudinaryUrl;
    private final UserMapper userMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserService userService(){
        return new UserService(userRepository());
    }

    @Bean
    public UserRepository userRepository(){
        return new MariadbRepository(userMapper);
    }

    @Bean
    public Cloudinary userPicResource(){
        return new Cloudinary(cloudinaryUrl);
    }
}
