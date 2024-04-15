package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Message;
import com.jsy.clonegram.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class JpaMessageRepositoryTest {
    @Autowired
    JpaMessageRepository messageRepository;

    @Autowired
    MessageService messageService;

    @Test
    void MessageTest(){
        Message message = new Message();
        message.setReceiverId(3L);
        message.setSenderId(2L);
        message.setContent("안녕하세요2");
        messageRepository.save(message);
    }

    @Test
    void MessagePageTest(){
//        Page<Message> messageBySenderIdAndReceiverId = messageService.findMessageBySenderIdAndReceiverId(3L, 2L);
//        for(Message message : messageBySenderIdAndReceiverId){
//            log.info(message.toString());
//        }
    }

}