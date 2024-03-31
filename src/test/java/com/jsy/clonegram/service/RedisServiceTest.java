package com.jsy.clonegram.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
@Transactional
class RedisServiceTest {

    @Autowired
    RedisService service;

    @Test
    void testRedis(){
        service.setEmailAuthCode("1234", "1234");
        String redisTemplate = service.getRedisTemplate("1234");
        log.info(redisTemplate);
    }
}