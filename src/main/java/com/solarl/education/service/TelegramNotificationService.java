package com.solarl.education.service;

import com.solarl.education.request.NotificationRequest;
import com.solarl.education.response.AdvertisementResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramNotificationService extends TelegramLongPollingBot implements NotificationService {

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.chat-id}")
    private String chatId;

    @Override
    public void onUpdateReceived(Update update) {
        // Обработка входящих сообщений
    }

    public void sendNotification(NotificationRequest notificationRequest) {
        send(notificationRequest.getChatId(), notificationRequest.getMessage());

    }

    public void sendAdvertisement(AdvertisementResponse advertisement) {
        send(chatId, advertisement.toString());
    }

    public void send(String chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Failed to send Telegram message", e);
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
