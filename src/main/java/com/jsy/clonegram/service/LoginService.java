package com.jsy.clonegram.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final RedisService redisService;

    public boolean getEmailAuthStatus(String email) {
        String emailAuthStatus = redisService.getEmailAuthStatus(email);
        return emailAuthStatus != null && emailAuthStatus.equals("success");
    }

    public boolean getDuplicationCheckId(String userName){
        String duplicationCheckId = redisService.getDuplicationCheckId(userName);
        return duplicationCheckId != null && duplicationCheckId.equals("success");
    }

    public boolean getDuplicationCheckEmail(String email){
        String duplicationCheckEmail = redisService.getDuplicationCheckEmail(email);
        return duplicationCheckEmail != null && duplicationCheckEmail.equals("success");
    }

    public void deleteAuthEmail(String email){
        redisService.deleteRecordByString(email+"_AuthStatus");
    }
}
