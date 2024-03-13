package com.jsy.clonegram.config.message;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;


@Configuration
public class MessageSourceConfig{

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages/messages","messages/errors"); // 메시지 프로퍼티 파일의 경로를 설정
        messageSource.setDefaultEncoding("UTF-8"); // 메시지 프로퍼티 파일의 인코딩을 설정

        return messageSource;
    }
}
