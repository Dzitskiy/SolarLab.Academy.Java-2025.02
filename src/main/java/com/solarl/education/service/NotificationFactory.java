package com.solarl.education.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationFactory {
    private final Map<String, NotificationService> notificationServices;

    private static final String NOTIFICATION_SERVICE = "NotificationService";

    public NotificationFactory(Map<String, NotificationService> notificationServices) {
        this.notificationServices = notificationServices;
    }

    public NotificationService getNotificationService(String type) {
        return notificationServices.getOrDefault(type + NOTIFICATION_SERVICE, notificationServices.get("emailNotificationService"));
    }
}
