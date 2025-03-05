package com.solarl.education.service;

import com.solarl.education.repository.NotificationRepository;
import com.solarl.education.request.NotificationRequest;
import com.solarl.education.response.NotificationResponse;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "notification.telegram", havingValue = "false")
public class EmailNotificationService extends NotificationService {

    @Autowired
    private ObjectProvider<EmailSender> emailSender1;

//    public void setEmailSender(EmailSender emailSender) {
//        this.emailSender = emailSender;
//    }

    public EmailNotificationService(NotificationRepository notificationRepository) {
        super(notificationRepository);
    }

    @PostConstruct
    public void preInitialisation() {
        System.out.println("Создание бина EmailNotificationService");
    }

    public NotificationResponse sendNotification(NotificationRequest notificationRequest) {
        EmailSender emailSender = emailSender1.getObject();
        if (emailSender == null) {
            throw new IllegalStateException("EmailSender не инициализирован!");
        }
        emailSender.doSomething();
        System.out.println("Отправка уведомления на почту: " + notificationRequest);
        return NotificationResponse.builder()
                .message(notificationRequest.getMessage())
                .status(200)
                .build();
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("Удаление бина EmailNotificationService");
    }

}
