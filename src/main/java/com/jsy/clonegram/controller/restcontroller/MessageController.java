package com.jsy.clonegram.controller.restcontroller;

import com.jsy.clonegram.dao.Message;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.UserMessageDto;
import com.jsy.clonegram.repository.UserRepository;
import com.jsy.clonegram.service.MessageService;
import com.jsy.clonegram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final UserRepository userRepository;
    private final UserService userService;


    @PostMapping("/find/message")
    public Page<UserMessageDto> findMessage(@RequestParam(name = "userId") String userId,@RequestParam(name = "msgNum") Integer msgNum) {
        Optional<User> byName = userRepository.findByName(userId);
        return byName.map(user -> messageService.findMessageBySenderIdAndReceiverId(userService.getUserIdOnSession(), user.getId(),msgNum)).orElse(null);
    }

    @PostMapping("/send/message/new")
    public Boolean sendMessageNew(@RequestParam(name = "text") String message,@RequestParam(name="targetId") String targetId) {
        messageService.sendMsg(message, targetId);
        return true;
    }
}
