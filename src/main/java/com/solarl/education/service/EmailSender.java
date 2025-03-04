package com.solarl.education.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.UUID;

//@Lazy
@Scope("session")
@Service
public class EmailSender {

    private final UUID id;

    public EmailSender() {
        this.id = UUID.randomUUID();
        System.out.println("EmailSender загружен!");
    }

    public void doSomething() {
        System.out.println("EmailSender используется! " + id);
    }
}
