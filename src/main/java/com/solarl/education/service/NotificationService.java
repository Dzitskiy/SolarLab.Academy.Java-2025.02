package com.solarl.education.service;

import com.solarl.education.request.NotificationRequest;
import com.solarl.education.response.NotificationResponse;

public interface NotificationService {

    NotificationResponse sendNotification(NotificationRequest notificationRequest);
}
