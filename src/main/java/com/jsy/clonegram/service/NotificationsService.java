package com.jsy.clonegram.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsy.clonegram.dao.*;
import com.jsy.clonegram.repository.JpaNotificationsRepository;
import com.jsy.clonegram.repository.PostRepository;
import com.jsy.clonegram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationsService {
    private final JpaNotificationsRepository jpaNotificationsRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final RedisService redisService;
    private final UserService userService;
    private final SseEmitterService emitterService;
    private final ObjectMapper objectMapper;
    private final PageReturnService pageReturnService;

    public void notifyMessage(Message message){
        Optional<User> user = userRepository.findById(message.getSenderId());
        if (user.isPresent()){
            Notifications notifications = new Notifications();
            notifications.setMessage(user.get().getUserName()+"님으로부터 메시지가 도착했습니다.");
            notifications.setUserId(message.getReceiverId());
            notifications.setReadAt(null);
            notifications.setType((byte) 0);
            notifications.setTid(message.getId());

            jpaNotificationsRepository.save(notifications);

            redisService.setNotifications(userRepository.findById(notifications.getUserId()).get().getUserName());

            getNotificationsCount(notifications.getUserId());
        }
    }

    public void notifyLikey(Likey likey) {
        Notifications notifications = new Notifications();
        Optional<Post> post = postRepository.findById(likey.getPostId());
        Optional<User> user = userRepository.findById(likey.getUserId());
        if(post.isPresent()){
            notifications.setMessage(user.get().getUserName()+"님이 "+post.get().getCaption().substring(0, Math.min(post.get().getCaption().length(), 10))+" 글에 좋아요를 누르셨습니다.");
            notifications.setUserId(post.get().getUserId());
            notifications.setReadAt(null);
            notifications.setType((byte) 1);
            notifications.setTid(likey.getId());

            jpaNotificationsRepository.save(notifications);

            redisService.setNotifications(userRepository.findById(notifications.getUserId()).get().getUserName());

            getNotificationsCount(notifications.getUserId());
        }
    }

    public void notifyComment(Comment comment){
        Notifications notifications = new Notifications();
        Optional<User> user = userRepository.findById(comment.getUserId());
        Optional<Post> post = postRepository.findById(comment.getPostId());
        if(user.isPresent() && post.isPresent()){
            notifications.setMessage(user.get().getUserName() + "님이 " + post.get().getCaption().substring(0, Math.min(post.get().getCaption().length(), 10)) + " 글에 댓글을 남겼습니다.");
            notifications.setUserId(post.get().getUserId());
            notifications.setReadAt(null);
            notifications.setType((byte) 2);
            notifications.setTid(comment.getId());
            jpaNotificationsRepository.save(notifications);
            redisService.setNotifications(userRepository.findById(notifications.getUserId()).get().getUserName());
            getNotificationsCount(notifications.getUserId());
        }
    }

    public void notifyFollow(Follow follow){
        Notifications notifications = new Notifications();
        Optional<User> user = userRepository.findById(follow.getFollowerId());
        if(user.isPresent()){
            notifications.setMessage(user.get().getUserName() + "님이 회원님을 팔로우 하기 시작했습니다.");
            notifications.setUserId(follow.getUserId());
            notifications.setReadAt(null);
            notifications.setType((byte) 3);
            notifications.setTid(follow.getId());
            jpaNotificationsRepository.save(notifications);
            redisService.setNotifications(userRepository.findById(notifications.getUserId()).get().getUserName());
            getNotificationsCount(notifications.getUserId());
        }
    }

    public void getNotificationsCount(Long targetId){
        try {
            Optional<User> byId = userRepository.findById(targetId);
            if (byId.isPresent() && emitterService.getEmitter(targetId) != null) {
                SseEmitter emitter = emitterService.getEmitter(targetId);
                Long notificationCount = redisService.getNotificationCount(byId.get().getUserName());
                String countNotify = objectMapper.writeValueAsString(notificationCount);
                emitter.send(SseEmitter.event().data(countNotify, MediaType.APPLICATION_JSON).id("notifications_count"));
            }
        }catch (IOException e){
            log.info("getNotificationsCount error",e);
        }
    }

    public List<Notifications> getNotifications(){
        List<Notifications> notifications = jpaNotificationsRepository.findAllByUserId(userService.getUserIdOnSession());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        notifications.forEach(notification -> {
            if (notification.getReadAt() == null) {
                notification.setReadAt(currentTimestamp);
            }
        });
        jpaNotificationsRepository.saveAll(notifications);
        redisService.deleteNotifications(userService.getUsernameOnSession());
        return notifications;
    }


    public Long getNotificationsCount(){
        Optional<User> byId = userRepository.findById(userService.getUserIdOnSession());
        return byId.map(user -> redisService.getNotificationCount(user.getUserName())).orElse(null);
    }

    public void deleteNotificationsCount(){
        redisService.deleteNotifications(userService.getUsernameOnSession());
        getNotificationsCount(userService.getUserIdOnSession());
    }

    public void deleteNotifications(){
        List<Notifications> allByUserId = jpaNotificationsRepository.findAllByUserId(userService.getUserIdOnSession());
        if (!allByUserId.isEmpty()){
            jpaNotificationsRepository.deleteAll(allByUserId);
        }
    }

    // message: 0, likey: 1, comment: 2, follow: 3
    public String findNotificationsPage(Long Id){
        Optional<Notifications> byId = jpaNotificationsRepository.findById(Id);
        if (byId.isPresent()){
            Notifications notifications = byId.get();
            Map<String, Object> data = new HashMap<>();
            switch (notifications.getType()){
                case 0:
                    Message messageById = pageReturnService.getMessage(notifications.getTid());
                    data.put("sender", userRepository.findById(messageById.getSenderId()).get().getUserName());
                    try {
                        return objectMapper.writeValueAsString(data);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                case 1:
                    Likey likeyById = pageReturnService.getLikey(notifications.getTid());
                    data.put("post", likeyById.getPostId());
                    try {
                        return objectMapper.writeValueAsString(data);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                case 2:
                    Comment commentById = pageReturnService.getComment(notifications.getTid());
                    data.put("post", commentById.getPostId());
                    try {
                        return objectMapper.writeValueAsString(data);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                case 3:
                    Follow followById = pageReturnService.getFollow(notifications.getTid());
                    data.put("follower", followById.getFollowerId());
                    try {
                        return objectMapper.writeValueAsString(data);
                    }catch (JsonProcessingException e){
                        throw new RuntimeException(e);
                    }
                default:
                    return null;
            }
        }
        return null;
    }
}
