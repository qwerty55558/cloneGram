package com.jsy.clonegram.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public void setRedisTemplate(String Email, String authCode) {
        if (redisTemplate.opsForValue().get(Email) != null) {
            redisTemplate.delete(Email);
        }
        redisTemplate.opsForValue().set(Email, authCode);
        redisTemplate.expire(Email, Duration.ofMinutes(3));


    }
    public String getRedisTemplate(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setEmailAuthStatus(String Email){
        redisTemplate.opsForValue().set(Email+"_AuthStatus","success");
    }

    public String getEmailAuthStatus(String Email){
        return redisTemplate.opsForValue().get(Email + "_AuthStatus");
    }

    public void setDuplicationCheckId(String id){
        redisTemplate.opsForValue().set(id,"success");
    }

    public void setDuplicated(String id){
        redisTemplate.opsForValue().set(id, "fail");
    }

    public String getDuplicationCheckId(String id){
        return redisTemplate.opsForValue().get(id);
    }

    public void setVersion(String version, String username){
        redisTemplate.opsForValue().set(username+"_pic",version);
    }

    public String getVersion(String username){
        return redisTemplate.opsForValue().get(username + "_pic");
    }

    public void deleteRecordByInfo(String Info){
        try {
            redisTemplate.delete(Info);
        } catch (Exception e) {
            log.info("redis delete error", e);
        }
    }

}
