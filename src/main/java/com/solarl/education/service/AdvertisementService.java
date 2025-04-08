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
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementMapper advertisementMapper;
    private final KafkaProducerService producerService;

    @PostConstruct
    public void preInitialisation() {
        log.info("Создание бина AdvertisementService");
    }

    @Cacheable(value = "advertisement", key = "#advertisementRequest.name")
    public void createAdvertisement(AdvertisementRequest advertisementRequest) {
        log.info("Создание объявления: " + advertisementRequest);
        Advertisement advertisement = advertisementMapper.toAdvertisement(advertisementRequest);
        advertisementRepository.save(advertisement);
        producerService.sendAdvertisement(advertisementMapper.toAdvertisementResponse(advertisement));
    }

    @Cacheable(value = "advertisement", key = "#id")
    public AdvertisementResponse getAdvertisement(Long id) {
        log.info("Получение объявления из бд по ИД: " + id);
        Optional<Advertisement> advertisement = advertisementRepository.findById(id);
        return advertisement.map(advertisementMapper::toAdvertisementResponse).orElse(null);
    }

    public List<AdvertisementResponse> getAdvertisements(Integer limit) {
        System.out.printf("Получение объявления из бд %s записей%n", limit);
        return advertisementRepository.findAll().stream().map(advertisementMapper::toAdvertisementResponse).toList();
    }

    @CachePut(value = "advertisement", key = "#id")
    public AdvertisementResponse updateAdvertisement(Long id, AdvertisementRequest advertisementRequest) {
        log.info("Редактирование объявления из бд по ИД: " + id);
        return AdvertisementResponse.builder()
                .cost(advertisementRequest.getCost())
                .name(advertisementRequest.getName())
                .category(advertisementRequest.getCategory())
                .subcategory(advertisementRequest.getSubcategory())
                .description(advertisementRequest.getDescription())
                .address(advertisementRequest.getAddress())
                .build();
    }

    @CacheEvict(value = "advertisement", key = "#id")
    public void deleteAdvertisement(Long id) {
        System.out.println("Удаление объявления из бд по ИД: " + id);
    }

    @Caching(
            cacheable = {
                    @Cacheable(value = "advertisement", key = "#id"),
                    @Cacheable(value = "basket", key = "#id")
            }
    )
    public AdvertisementResponse addAdvertisementToBasket(Long id) {
        log.info("Добавление объявления в корзину: " + id);
        Optional<Advertisement> advertisement = advertisementRepository.findById(id);
        if(advertisement.isEmpty()) {
            log.error("Объявления не существует: " + id);
            return null;
        }

        log.info("Кладём в корзину");
        return advertisementMapper.toAdvertisementResponse(advertisement.get());
    }

    @Caching(
            cacheable = @Cacheable(value = "payment", keyGenerator = "customKeyGenerator"),
            evict = {
                    @CacheEvict(value = "advertisement", allEntries = true),
                    @CacheEvict(value = "basket", allEntries = true)
            }
    )
    public List<AdvertisementResponse> payPurchasesFromBasket(List<Long> ids) {
        log.info("Оплата объявлений из корзины: " + ids.size());
        List<Advertisement> advertisements = advertisementRepository.findByIdIn(ids);
        if(advertisements.isEmpty()) {
            log.error("Объявления не найдены");
            return null;
        }

        log.info("Оплачиваем объявления из корзины, удаляем объявления из списка");
        advertisementRepository.deleteAll(advertisements);
        return advertisementMapper.toListAdvertisementResponse(advertisements);
    }

    @PreDestroy
    public void preDestroy() {
        log.info("Удаление бина AdvertisementService");
    }

}
