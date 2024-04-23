package com.jsy.clonegram.config.security;

import com.jsy.clonegram.config.security.handler.AccessRejectedHandler;
import com.jsy.clonegram.config.security.handler.MyLogoutSuccessHandler;
import com.jsy.clonegram.dao.Grade;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * SpringSecurity의 Config
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AccessDeniedHandler rejectHandler;
    private final MyLogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(withDefaults -> withDefaults
                        .invalidSessionUrl("/login")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                )
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/status", "/images/**", "sign/**", "/sendEmail", "/checkCode", "/validation/**").permitAll()
                        .requestMatchers("/dashboard/**").hasRole(Grade.Admin.getCode()) // dashboard가 포함된 URI에서는 role 요건이 충족되어야 접속 가능
                        .anyRequest().authenticated()    // 어떠한 요청이라도 인증필요
                )
                .exceptionHandling(exceptionConfig ->
                        exceptionConfig.accessDeniedHandler(rejectHandler)) // 엑세스 디나이된 유저들을 핸들링 하는 코드
                .formLogin(login -> login
                        .loginPage("/login")    // [A] 커스텀 로그인 페이지 지정
                        .loginProcessingUrl("/login")    // [B] submit 받을 url
                        .usernameParameter("id")    // [C] submit할 아이디
                        .passwordParameter("pw")    // [D] submit할 비밀번호
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(withDefaults -> withDefaults
                        .logoutSuccessHandler(logoutSuccessHandler));

        return http.build();
    }
}