package com.jsy.clonegram.service;

import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.UserUpdateDto;
import com.jsy.clonegram.generator.GenerateAuthCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisService redisService;
    private final UserService userService;

    public String sendAuthEmail(String to) {
        SimpleMailMessage msg = new SimpleMailMessage();
        String code = new GenerateAuthCode().executeGenerate();
        redisService.setEmailAuthCode(to, code);

        log.info("code = {}",code);

        msg.setFrom("qwerty446688@naver.com");
        msg.setTo(to);
        msg.setSubject("Clonegram 인증 번호입니다.");
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

    public String sendResetPasswordEmail(String email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        String code = new GenerateAuthCode().executeGenerate();

        Optional<User> userByEmail = userService.getUserByEmail(email);
        if (userByEmail.isPresent()) {

            User user = userByEmail.get();
            UserUpdateDto userUpdateDto = new UserUpdateDto();
            userUpdateDto.setPassword(userService.encodePassword(code));
            Boolean result = userService.updateUser(user, userUpdateDto);
            if (result){
                msg.setFrom("qwerty446688@naver.com");
                msg.setTo(email);
                msg.setSubject("Clonegram 비밀번호 재설정 요청에 대한 답변입니다.");
                msg.setText("임시 비밀번호 : " + code +
                        "\n" +
                        "위의 비밀번호는 임시 비밀번호입니다. 로그인 후 프로필 설정에서 비밀번호를 변경해주세요."
                );
                javaMailSender.send(msg);
                log.info("reset pw code = {}",code);
                return code;
            }
        }



        return null;
    }

}
