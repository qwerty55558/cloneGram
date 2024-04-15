package com.jsy.clonegram.service;

import com.jsy.clonegram.dao.Message;
import com.jsy.clonegram.repository.JpaMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MessageServiceTest {

    @Autowired
    MessageService messageService;
    @Autowired
    JpaMessageRepository jpaMessageRepository;

    @Test
    void findMessageBySenderIdAndReceiverId() {
//        List<Message> byReceiverIdAndSenderId = jpaMessageRepository.findByReceiverIdAndSenderId(2L, 3L);
//        log.info(byReceiverIdAndSenderId.toString());
    }
}