package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface JpaNotificationsRepository extends JpaRepository<Notifications,Long> {
    List<Notifications> findAllByUserId(Long userId);
    void deleteNotificationsByTid(Long tid);
}
