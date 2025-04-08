package com.solarl.education.consumer;

import com.solarl.education.response.AdvertisementResponse;
import com.solarl.education.service.EmailNotificationService;
import com.solarl.education.service.TelegramNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final EmailNotificationService emailNotificationService;
    private final TelegramNotificationService telegramNotificationService;

    @KafkaListener(topics = "${kafka.consumer.topic.advertisement}",
            groupId = "email-notification-group",
            containerFactory = "emailNotificationKafkaListenerContainerFactory"
    )
    public void consumeAdvertisementForEmail(AdvertisementResponse advertisement) {
        try {
            emailNotificationService.sendAdvertisement(advertisement);
        } catch (Exception e) {
            log.error("Ошибка при обработке данных для email: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "${kafka.consumer.topic.advertisement}",
            groupId = "telegram-notification-group",
            containerFactory = "telegramNotificationKafkaListenerContainerFactory"
    )
    public void consumeAdvertisementForTelegram(AdvertisementResponse advertisement) {
        try {
            telegramNotificationService.sendAdvertisement(advertisement);
        } catch (Exception e) {
            log.error("Ошибка при обработке данных для telegram: {}", e.getMessage());
        }
    }
}
