package com.jsy.clonegram.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public void setEmailAuthCode(String Email, String authCode) {
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

    public void setVersion(String version, String name){
        redisTemplate.opsForValue().set(name+"_pic",version);
    }

    public String getVersion(String username){
        return redisTemplate.opsForValue().get(username + "_pic");
    }


    public void deleteRecordByString(String Info){
        try {
            redisTemplate.delete(Info);
        } catch (Exception e) {
            log.info("redis delete error", e);
        }
    }

    public void setNotifications(String userName){
        String notificationCount = redisTemplate.opsForValue().get(userName + "_notification");
        if (notificationCount == null) {
            redisTemplate.opsForValue().set(userName + "_notification", "1");
        }else {
            int count = Integer.parseInt(Objects.requireNonNull(redisTemplate.opsForValue().get(userName + "_notification")));
            redisTemplate.opsForValue().set(userName + "_notification", String.valueOf(++count));
        }
    }

    public void deleteNotifications(String userName){
        try{
            redisTemplate.delete(userName + "_notification");
        }catch (Exception e){
            log.info("redis delete error", e);
        }
    }
    public Long getNotificationCount(String userName){
        String s = redisTemplate.opsForValue().get(userName + "_notification");
        if (s == null) {
            return null;
        }
        return Long.parseLong(s);
    }

    public void setFollower(Long userId, Long followerSize){
        redisTemplate.opsForValue().set(userId+"_follower",followerSize.toString());
    }

    public void setFollowing(Long userId, Long followingSize) {
        redisTemplate.opsForValue().set(userId+"_following",followingSize.toString());
    }

    public String getFollower(Long userId){

        String s = redisTemplate.opsForValue().get(userId + "_follower");
        if (s == null) {
            return "0";
        }
        return s;
    }

    public String getFollowing(Long userId){
        String s = redisTemplate.opsForValue().get(userId + "_following");
        if (s == null) {
            return "0";
        }
        return s;
    }

}
