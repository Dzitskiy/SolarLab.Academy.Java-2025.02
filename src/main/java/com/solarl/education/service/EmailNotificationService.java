package com.solarl.education.service;

import com.solarl.education.request.NotificationRequest;
import com.solarl.education.response.AdvertisementResponse;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailNotificationService implements NotificationService {

    private final JavaMailSender mailSender;

    public void sendNotification(NotificationRequest notificationRequest) {
        send(notificationRequest.getEmail(), "NOTIFICATION", notificationRequest.getMessage());
    }

    public void sendAdvertisement(AdvertisementResponse advertisement) {
        send("test@example.com", "ADVERTISEMENT", advertisement.toString());
    }

    public void send(String email, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

}
