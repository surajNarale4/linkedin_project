package com.linkedIn.notification_service.service;


import com.linkedIn.notification_service.entity.Notification;
import com.linkedIn.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void addNotification(Notification notification) {

        notificationRepository.save(notification);
    }
}
