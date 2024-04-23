package com.jsy.clonegram.repository.listener;

import com.jsy.clonegram.dao.Comment;
import com.jsy.clonegram.repository.JpaPostRepository;
import com.jsy.clonegram.service.NotificationsService;
import com.jsy.clonegram.service.RedisService;
import jakarta.persistence.PostPersist;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.repository.cdi.RedisRepositoryBean;

public class CommentListener implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @PostPersist
    public void onCommentCreated(Comment comment) {
        NotificationsService notificationsService = applicationContext.getBean(NotificationsService.class);
        notificationsService.notifyComment(comment);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
