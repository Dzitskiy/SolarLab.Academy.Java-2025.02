package com.solarl.education.controller;

import com.solarl.education.request.NotificationRequest;
import com.solarl.education.response.NotificationResponse;
import com.solarl.education.service.NotificationFactory;
import com.solarl.education.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("v1/notification")
@Tag(name = "Сервис уведомлений", description = "API доступа к уведомлениям")
public class NotificationController {

    private final NotificationFactory notificationFactory;

    @PostMapping("/send")
    @Operation(summary = "Отправка уведомления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успех"),
            @ApiResponse(responseCode = "400", description = "Неверно переданные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка работы сервиса")
    })
    public NotificationResponse sendNotification(
            @Parameter(description = "Запрос на отправку уведомления")
            @RequestBody NotificationRequest notificationRequest,
            @Parameter(description = "telegram/email")
            @RequestParam String type) {
        NotificationService notificationService = notificationFactory.getNotificationService(type);
        return notificationService.sendNotification(notificationRequest);
    }

}
