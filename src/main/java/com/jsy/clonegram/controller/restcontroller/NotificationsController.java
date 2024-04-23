package com.jsy.clonegram.controller.restcontroller;

import com.jsy.clonegram.dao.Notifications;
import com.jsy.clonegram.service.NotificationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NotificationsController {

    private final NotificationsService notificationsService;

    @GetMapping("/find/notifications")
    public List<Notifications> getNotifications() {
        notificationsService.deleteNotificationsCount();
        return notificationsService.getNotifications();
    }

    @GetMapping("/find/notifications/count")
    public Long countNotifications() {
        return notificationsService.getNotificationsCount();
    }

    @GetMapping("/delete/notifications")
    public void deleteNotifications() {
        notificationsService.deleteNotifications();
    }

    @PostMapping("/find/notifications/page")
    public String findNotificationsPage(@RequestParam(name = "notifications") Long id) {
        log.info("id = {}", id);
        return notificationsService.findNotificationsPage(id);
    }
}
