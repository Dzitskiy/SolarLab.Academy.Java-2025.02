package com.solarl.education.service;

import com.solarl.education.entity.Advertisement;
import com.solarl.education.enums.CategoryEnum;
import com.solarl.education.mapper.AdvertisementMapper;
import com.solarl.education.repository.AdvertisementRepository;
import com.solarl.education.request.AdvertisementRequest;
import com.solarl.education.request.AdvertisementUpdateRequest;
import com.solarl.education.response.AdvertisementResponse;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@CacheConfig(cacheNames = "adv")
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementMapper advertisementMapper;
    private final CacheManager cacheManager;

    @PostConstruct
    public void preInitialisation() {
        log.info("Создание бина AdvertisementService");
    }

    @Cacheable
    public AdvertisementResponse createAdvertisement(AdvertisementRequest request) {
        log.info("Создание объявления: " + request);
        Advertisement advertisement = advertisementMapper.toAdvertisement(request);
        return advertisementMapper.toAdvertisementResponse(advertisementRepository.save(advertisement));
    }

    @Cacheable(key = "#name")
    public AdvertisementResponse getAdvertisement(String name) {
        log.info("Получение объявления из бд по имени: " + name);
        Optional<Advertisement> advertisement = advertisementRepository.findByName(name);
        return advertisement.map(advertisementMapper::toAdvertisementResponse).orElse(null);
    }

        @Cacheable(key = "#category + '_' + #cost", condition = "#category.name() != 'TRANSPORT'", unless = "#result.size == 0")
    public List<AdvertisementResponse> getAdvertisementsByCategoryAndCost(CategoryEnum category, Integer cost) {
        log.info("Получение объявлений из бд по цене больше чем {} и категории {} ", cost, category);

        List<Advertisement> advertisements = advertisementRepository
                .findByCategoryAndCostGreaterThanEqual(category, cost);

        if(advertisements.isEmpty()) {
            log.error("Нет объявлений, удовлетворяющих указанные условия");
            return Collections.emptyList();
        }

        return advertisementMapper.toListAdvertisementResponse(advertisements);
    }

    @Caching(
            cacheable = {
                    @Cacheable(key = "#name"),
                    @Cacheable(value = "basket", key = "#name")
            }
    )
    public AdvertisementResponse addAdvertisementToBasket(String name) {
        log.info("Добавление объявления в корзину: " + name);
        Optional<Advertisement> advertisement = advertisementRepository.findByName(name);
        if(advertisement.isEmpty()) {
            log.error("Объявления не существует: " + name);
            return null;
        }

        log.info("Кладём в корзину");
        return advertisementMapper.toAdvertisementResponse(advertisement.get());
    }

    @Caching(
            cacheable = @Cacheable(value = "payment", keyGenerator = "paymentKeyGenerator"),
            evict = {
                    @CacheEvict(allEntries = true),
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

    @CachePut(key = "#name")
    public AdvertisementResponse updateAdvertisement(String name, AdvertisementUpdateRequest request) {
        log.info("Редактирование объявления из бд по имени: " + name);
        Optional<Advertisement> advertisementOpt = advertisementRepository.findByName(name);
        if (advertisementOpt.isEmpty()) {
            log.error("Объявление не найдено: " + name);
            return null;
        }
        Advertisement advertisement = advertisementOpt.get();
        if (request.getName() != null) advertisement.setName(request.getName());
        if (request.getCost() != null) advertisement.setCost(request.getCost());
        if (request.getDescription() != null) advertisement.setDescription(request.getDescription());
        return advertisementMapper.toAdvertisementResponse(advertisementRepository.save(advertisement));
    }

    @Transactional
    @CacheEvict(key = "#name")
    public void deleteAdvertisement(String name) {
        log.info("Удаление объявления из бд по имени: " + name);

        advertisementRepository.deleteByName(name);
    }

    public List<AdvertisementResponse> getAdvertisements(Integer limit) {
        log.info("Получение объявления из бд {} записей", limit);
        return List.of(
                AdvertisementResponse.builder()
                        .cost(20)
                        .name("Kia Rio")
                        .category("Car")
                        .subcategory("Sport Car")
                        .description("Super puper sport car")
                        .createDateTime(LocalDateTime.now())
                        .build(),
                AdvertisementResponse.builder()
                        .cost(25)
                        .name("Cherry Tiggo 4")
                        .category("Car")
                        .subcategory("Crossover")
                        .description("Pro max ultra luxury edition")
                        .createDateTime(LocalDateTime.now())
                        .build()
        );
    }

    @PreDestroy
    public void preDestroy() {
        log.info("Удаление бина AdvertisementService");
    }

}
