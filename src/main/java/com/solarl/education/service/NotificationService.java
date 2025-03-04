package com.solarl.education.service;

import com.solarl.education.repository.NotificationRepository;
import com.solarl.education.request.NotificationRequest;
import com.solarl.education.response.NotificationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public abstract class NotificationService {

    private final NotificationRepository notificationRepository;

    public abstract NotificationResponse sendNotification(NotificationRequest notification);

}
