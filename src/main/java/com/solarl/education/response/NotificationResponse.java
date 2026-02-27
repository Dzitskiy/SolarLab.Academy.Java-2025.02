package com.solarl.education.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationResponse {
    private String message;
    private int status;
}
