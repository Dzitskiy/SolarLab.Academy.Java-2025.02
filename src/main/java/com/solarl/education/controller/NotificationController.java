package com.solarl.education.controller;

import com.solarl.education.request.NotificationRequest;
import com.solarl.education.response.NotificationResponse;
import com.solarl.education.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/notification")
@RequiredArgsConstructor
@Tag(name = "Сервис уведомлений", description = "API доступа к уведомлениям")
public class NotificationController {

    private final NotificationService emailNotificationService;

    @PostConstruct
    public void init() {
        System.out.println("Создали бин AdvertisementController");
    }

    @PostMapping
    @Operation(summary = "Создание объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Создано"),
            @ApiResponse(responseCode = "400", description = "неверно переданные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка работы сервиса")
    })
    public NotificationResponse createAdvertisement(
            @Parameter(description = "Модель для отправки уведомления")
            @RequestBody NotificationRequest notificationRequest
    ) {
        return emailNotificationService.sendNotification(notificationRequest);
    }

}
