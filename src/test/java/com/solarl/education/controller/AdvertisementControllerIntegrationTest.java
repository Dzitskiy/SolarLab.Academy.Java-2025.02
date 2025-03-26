package com.solarl.education.controller;

import com.solarl.education.enums.CategoryEnum;
import com.solarl.education.request.AdvertisementRequest;
import com.solarl.education.server.IntegrationTestServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdvertisementControllerIntegrationTest extends IntegrationTestServer {

    @Autowired
    private AdvertisementController advertisementController;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testCreateAdvertisement() {

        var createAdvertisementRequest = AdvertisementRequest.builder()
                .name("Name1")
                .category(CategoryEnum.ANIMALS.name())
                .subcategory(CategoryEnum.OTHER.name())
                .cost(100)
                .description("Description")
                .address("Address")
                .createDateTime(LocalDateTime.now())
                .build();

        advertisementController.createAdvertisement(createAdvertisementRequest);

        var response = advertisementController.getAdvertisementPage(10);
        assertEquals(1, response.size());
        assertEquals(createAdvertisementRequest.getName(), response.get(0).getName());
        assertEquals(createAdvertisementRequest.getCost(), response.get(0).getCost());
        assertEquals(createAdvertisementRequest.getAddress(), response.get(0).getAddress());
        assertEquals(createAdvertisementRequest.getCategory(), response.get(0).getCategory());

    }

}
