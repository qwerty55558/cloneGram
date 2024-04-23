package com.jsy.clonegram.repository.listener;

import com.jsy.clonegram.dao.Likey;
import com.jsy.clonegram.service.NotificationsService;
import jakarta.persistence.PostPersist;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class LikeyListener implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @PostPersist
    public void onLikeyCreated(Likey likey) {
        NotificationsService bean = applicationContext.getBean(NotificationsService.class);
        bean.notifyLikey(likey);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
