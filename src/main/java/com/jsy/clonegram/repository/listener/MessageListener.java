package com.jsy.clonegram.repository.listener;

import com.jsy.clonegram.dao.Message;
import com.jsy.clonegram.service.MessageService;
import com.jsy.clonegram.service.NotificationsService;
import jakarta.persistence.PostPersist;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MessageListener implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @PostPersist
    public void onMessageCreated(Message message) {
        MessageService messageService = applicationContext.getBean(MessageService.class);
        NotificationsService bean = applicationContext.getBean(NotificationsService.class);
        messageService.receiveMsg(message);
        bean.notifyMessage(message);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
