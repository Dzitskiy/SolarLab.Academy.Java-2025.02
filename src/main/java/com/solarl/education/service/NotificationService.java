package com.solarl.education.service;

import com.solarl.education.request.NotificationRequest;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {

    void sendNotification(NotificationRequest notification);

}
