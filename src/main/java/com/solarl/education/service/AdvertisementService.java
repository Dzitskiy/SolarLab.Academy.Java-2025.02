package com.solarl.education.service;

import com.solarl.education.repository.AdvertisementRepository;
import com.solarl.education.request.AdvertisementRequest;
import com.solarl.education.response.AdvertisementResponse;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class AdvertisementService {

    private ObjectProvider<AdvertisementRepository> advertisementRepositoryProvider;

    @Lazy
    @Autowired
    public void setAdvertisementRepository(ObjectProvider<AdvertisementRepository> advertisementRepositoryProvider) {
        this.advertisementRepositoryProvider = advertisementRepositoryProvider;
    }

    @PostConstruct
    public void init() {
        System.out.println("Создали бин AdvertisementService");
    }

    public void createAdvertisement(AdvertisementRequest advertisementRequest) {
        AdvertisementRepository advertisementRepository = advertisementRepositoryProvider.getObject();
        advertisementRepository.doSomething();
        advertisementRepository = advertisementRepositoryProvider.getObject();
        advertisementRepository.doSomething();
        System.out.println("Создаем объявление: " + advertisementRequest);
    }

    public AdvertisementResponse getAdvertisementById(Long id) {
        System.out.println("Получаем объявление по id: " + id);
        return AdvertisementResponse.builder()
                .name("Kia")
                .description("Super car")
                .category("Car")
                .address("Sevastopol")
                .id(id)
                .cost(10)
                .build();
    }

    public AdvertisementResponse updateAdvertisementById(Long id, AdvertisementRequest advertisementRequest) {
        System.out.println("Изменение объявления с id: " + id);
        return AdvertisementResponse.builder()
                .name(advertisementRequest.getName())
                .description(advertisementRequest.getDescription())
                .category(advertisementRequest.getCategory())
                .address(advertisementRequest.getAddress())
                .subcategory(advertisementRequest.getSubcategory())
                .build();
    }

    public void deleteAdvertisementById(Long id) {
        System.out.println("Удаляем объявление с id: " + id);
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Удаляем бин AdvertisementService");
    }
}
