package com.jsy.clonegram.service;

import com.jsy.clonegram.repository.MariadbRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class EmailServiceTest {

    @Autowired
    EmailService emailService;

    @Test
    void sendAuthEmail() {
        emailService.sendAuthEmail("amajang2012@gmail.com", Locale.KOREA);
    }
}