package com.solarl.education.service;

import com.solarl.education.entity.Advertisement;
import com.solarl.education.mapper.AdvertisementMapper;
import com.solarl.education.producer.KafkaProducerService;
import com.solarl.education.repository.AdvertisementRepository;
import com.solarl.education.request.AdvertisementRequest;
import com.solarl.education.response.AdvertisementResponse;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementMapper advertisementMapper;
    private final KafkaProducerService producerService;

    @PostConstruct
    public void preInitialisation() {
        System.out.println("Создание бина AdvertisementService");
    }

    public void createAdvertisement(AdvertisementRequest advertisementRequest) {
        System.out.println("Создание объявления: " + advertisementRequest);
        Advertisement advertisement = advertisementMapper.toAdvertisement(advertisementRequest);
        advertisementRepository.save(advertisement);
        producerService.sendAdvertisement(advertisementMapper.toAdvertisementResponse(advertisement));
    }

    public AdvertisementResponse getAdvertisement(Long id) {
        System.out.println("Получение объявления из бд по ИД: " + id);
        Optional<Advertisement> advertisement = advertisementRepository.findById(id);
        return advertisement.map(advertisementMapper::toAdvertisementResponse).orElse(null);
    }

    public List<AdvertisementResponse> getAdvertisements(Integer limit) {
        System.out.printf("Получение объявления из бд %s записей%n", limit);
        return advertisementRepository.findAll().stream().map(advertisementMapper::toAdvertisementResponse).toList();
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
