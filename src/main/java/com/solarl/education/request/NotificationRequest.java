package com.solarl.education.request;

import lombok.Data;

@Data
public class NotificationRequest {
    private String clientName;
    private String email;
    private String phoneNumber;
}
