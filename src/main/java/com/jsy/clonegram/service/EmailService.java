package com.jsy.clonegram.service;

import com.jsy.clonegram.generator.GenerateAuthCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisService redisService;

    public String sendAuthEmail(String to) {
        SimpleMailMessage msg = new SimpleMailMessage();
        String code = new GenerateAuthCode().executeGenerate();
        redisService.setRedisTemplate(to, code);

        log.info("code = {}",code);

        msg.setFrom("qwerty446688@naver.com");
        msg.setTo(to);
        msg.setSubject("cloneGram 인증 번호입니다.");
        msg.setText("인증번호 : "+code);
        javaMailSender.send(msg);
        return code;
    }

    public Boolean checkCode(String email, String paramCode) {
        String redisTemplate = redisService.getRedisTemplate(email);
        if (paramCode.equals(redisTemplate)) {
            redisService.setEmailAuthStatus(email);
            return true;
        }
        return false;
    }

}
