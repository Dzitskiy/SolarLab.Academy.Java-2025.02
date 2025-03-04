package com.solarl.education.service;

import com.solarl.education.repository.NotificationRepository;
import com.solarl.education.request.NotificationRequest;
import com.solarl.education.response.NotificationResponse;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "notification.telegram", havingValue = "true")
public class TelegramNotificationService extends NotificationService {

    public TelegramNotificationService(NotificationRepository notificationRepository) {
        super(notificationRepository);
    }

    @PostConstruct
    public void preInitialisation() {
        System.out.println("Создание бина TelegramNotificationService");
    }

    @Override
    public NotificationResponse sendNotification(NotificationRequest notificationRequest) {
        System.out.println("Отправка уведомления в телегу: " + notificationRequest);
        return NotificationResponse.builder()
                .message(notificationRequest.getMessage())
                .status(200)
                .build();
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("Удаление бина TelegramNotificationService");
    }
}
