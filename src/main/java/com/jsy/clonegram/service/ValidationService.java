package com.jsy.clonegram.service;

import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.DefaultMessageDto;
import com.jsy.clonegram.dto.ValidationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidationService {
    private final UserService userService;
    private final RedisService redisService;

    @Transactional
    public DefaultMessageDto getValidateUsername(ValidationRequestDto validationRequestDto) {
        Optional<User> byName = userService.getUserByUserName(validationRequestDto.getId());
        if (byName.isPresent()) {
            return new DefaultMessageDto("fail");
        }else {
            redisService.setDuplicationCheckId(validationRequestDto.getId());
            return new DefaultMessageDto("success");
        }
    }

    @Transactional
    public DefaultMessageDto getValidateEmail(ValidationRequestDto validationRequestDto) {
        Optional<User> byEmail = userService.getUserByEmail(validationRequestDto.getEmail());
        if (byEmail.isPresent()) {
            return new DefaultMessageDto("fail");
        }else {
            redisService.setDuplicationCheckEmail(validationRequestDto.getEmail());
            return new DefaultMessageDto("success");
        }
    }

    @Transactional
    public boolean getValidateEmail(String email) {
        return userService.getUserByEmail(email).isEmpty();
    }
}
