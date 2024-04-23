package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaNotificationsRepository extends JpaRepository<Notifications,Long> {
    List<Notifications> findAllByUserId(Long userId);
}
