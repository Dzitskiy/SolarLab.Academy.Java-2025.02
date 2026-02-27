package com.solarl.education.repository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Lazy
@Repository
@Scope("session")
public class AdvertisementRepository {

    private final UUID id;

    public AdvertisementRepository() {
        this.id = UUID.randomUUID();
        System.out.println("Бин загружен: " + this.id);
    }

    @PostConstruct
    public void init() {
        System.out.println("Создали бин AdvertisementRepository: " + id);
    }

    public void doSomething() {
        System.out.println("Вызвали метод репозитория: " + id);
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Удаляем бин AdvertisementRepository");
    }
}
