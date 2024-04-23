package com.jsy.clonegram.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsy.clonegram.config.security.MyUserDetailService;
import com.jsy.clonegram.dao.Message;
import com.jsy.clonegram.dto.UserMessageDto;
import com.jsy.clonegram.repository.JpaMessageRepository;
import com.jsy.clonegram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final JpaMessageRepository messageRepository;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final SseEmitterService sseEmitterService;

    private final UserRepository userRepository;

    public Page<UserMessageDto> findMessageBySenderIdAndReceiverId(Long senderId, Long receiverId, Integer pageNumber) {

        try {
            // JpaRepository의 메서드를 사용하여 페이지를 가져옵니다.
            List<Message> list = messageRepository.findMessageByReceiverIdAndSenderId(receiverId, senderId);

            // 가져온 페이지의 내용을 UserMessageDto로 변환합니다.
            List<UserMessageDto> userMessageDtos = list.stream()
                    .map(this::convertToUserMessageDto)
                    .collect(Collectors.toList());
            PageRequest pageRequest = PageRequest.of(pageNumber, 10);
            int start = (int) pageRequest.getOffset();
            int end = Math.min((start + pageRequest.getPageSize()), userMessageDtos.size());
            return new PageImpl<>(userMessageDtos.subList(start, end), pageRequest, userMessageDtos.size());
        } catch (Exception e) {
            log.info("인덱스 넘어간 요청");
            return null;
        }
    }


    private UserMessageDto convertToUserMessageDto(Message message) {

        if (message.getSenderId().equals(userService.getUserIdOnSession())) {
            return new UserMessageDto(true, message.getContent());
        }
        return new UserMessageDto(false, message.getContent());
    }


    public void receiveMsg(Message message) {
        if (sseEmitterService.isEmitterActive(message.getReceiverId())) {
            SseEmitter sseEmitter = sseEmitterService.getEmitter(message.getReceiverId());
            try {
                // 메시지와 유저 이름을 JSON 객체로 생성
                Map<String, Object> jsonMap = new HashMap<>();
                jsonMap.put("message", message.getContent());
                jsonMap.put("userName", userRepository.findById(message.getSenderId()).orElseThrow().getUserName());

                // JSON 객체를 문자열로 변환하여 보냄
                String jsonData = objectMapper.writeValueAsString(jsonMap);

                // SSEEmitter로 데이터 전송
                sseEmitter.send(SseEmitter.event().data(jsonData, MediaType.APPLICATION_JSON).id("message"));
            } catch (IOException e) {
                log.info("receiveMsg Error :",e);
            }
        }
    }

    public void sendMsg(String msg, String receiveId) {
        Message message = new Message();
        message.setSenderId(userService.getUserIdOnSession());
        message.setReceiverId(userRepository.findByName(receiveId).get().getId());
        message.setContent(msg);
        messageRepository.save(message);
    }

}
