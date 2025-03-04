package com.solarl.education.service;

import com.solarl.education.request.AdvertisementRequest;
import com.solarl.education.response.AdvertisementResponse;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    @PostConstruct
    public void preInitialisation() {
        System.out.println("Создание бина AdvertisementService");
    }

    public void createAdvertisement(AdvertisementRequest advertisementRequest) {
        System.out.println("Создание объявления: " + advertisementRequest);
    }

    public AdvertisementResponse getAdvertisement(Long id) {
        System.out.println("Получение объявления из бд по ИД: " + id);
        return AdvertisementResponse.builder()
                .cost(20)
                .name("Kia Rio")
                .category("Car")
                .subcategory("Sport Car")
                .description("Super puper sport car")
                .address("BM 23")
                .createDateTime(LocalDateTime.now())
                .build();
    }

    public AdvertisementResponse updateAdvertisement(Long id, AdvertisementRequest advertisementRequest) {
        System.out.println("Редактирование объявления из бд по ИД: " + id);
        return AdvertisementResponse.builder()
                .cost(advertisementRequest.getCost())
                .name(advertisementRequest.getName())
                .category(advertisementRequest.getCategory())
                .subcategory(advertisementRequest.getSubcategory())
                .description(advertisementRequest.getDescription())
                .address(advertisementRequest.getAddress())
                .build();
    }

    public void deleteAdvertisement(Long id) {
        System.out.println("Удаление объявления из бд по ИД: " + id);
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("Удаление бина AdvertisementService");
    }

}
