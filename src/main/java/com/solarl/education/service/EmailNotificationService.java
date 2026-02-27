package com.solarl.education.service;

import com.solarl.education.request.NotificationRequest;
import com.solarl.education.response.NotificationResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "notification.telegram", havingValue = "false")
public class EmailNotificationService implements NotificationService {

    @Override
    public NotificationResponse sendNotification(NotificationRequest notificationRequest) {
        return NotificationResponse.builder()
                .status(200)
                .message("Успешно отправили сообщение на EMAIL")
                .build();
    }
}
