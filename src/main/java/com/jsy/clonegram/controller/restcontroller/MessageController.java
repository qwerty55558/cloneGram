package com.jsy.clonegram.controller.restcontroller;

import com.jsy.clonegram.dto.UserMessageDto;
import com.jsy.clonegram.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;


    @PostMapping("/find/message")
    public Page<UserMessageDto> findMessage(@RequestParam(name = "userId") String userId,@RequestParam(name = "msgNum") Integer msgNum) {
        return messageService.findMessageBySenderIdAndReceiverId(userId, msgNum);
    }

    @PostMapping("/send/message/new")
    public Boolean sendMessageNew(@RequestParam(name = "text") String message,@RequestParam(name="targetId") String targetId) {
        messageService.sendMsg(message, targetId);
        return true;
    }
}
