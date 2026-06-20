package com.linkedIn.notification_service.consumer;


import com.linkedIn.notification_service.entity.Notification;
import com.linkedIn.notification_service.service.NotificationService;
import com.linkedIn.postsService.event.PostCreated;
import com.linkedIn.postsService.event.PostLiked;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@EnableKafka
@Component
@Slf4j
public class NotificationConsumer {


    private final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "post_created_event")
    public void postCreatedListener(PostCreated postCreated){
        log.info("post is created with id: {} and it should go to {} and created by {}",postCreated.getPostId(),postCreated.getUserId(),postCreated.getPostOwnerId());
        String message=String.format("user with id %d created new post %s",postCreated.getPostOwnerId(),postCreated.getContent());
        Notification notification= Notification.builder()
                .userId(postCreated.getUserId())
                .message(message)
                .build();
        notificationService.addNotification(notification);
    }

    @KafkaListener(topics="post_liked_topic")
    public void postLikedListener(PostLiked postLiked){
        log.info("user with id {} likes your post {}",postLiked.getLikedByUserId(),postLiked.getPostId());
        String message = String.format("user with id %d liked your post %d",postLiked.getLikedByUserId(),postLiked.getPostId());
        Notification notification = Notification.builder()
                .message(message)
                .userId(postLiked.getPostOwnerId())
                .build();
    }
}
