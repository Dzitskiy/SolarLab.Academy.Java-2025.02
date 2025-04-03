package com.solarl.education.service;

import com.solarl.education.entity.Advertisement;
import com.solarl.education.mapper.AdvertisementMapper;
import com.solarl.education.producer.KafkaProducerService;
import com.solarl.education.repository.AdvertisementRepository;
import com.solarl.education.request.AdvertisementRequest;
import com.solarl.education.response.AdvertisementResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class AdvertisementServiceTest {

    @Mock
    private AdvertisementRepository advertisementRepository;

    @Mock
    private AdvertisementMapper advertisementMapper;

    @Mock
    private KafkaProducerService producerService;

    @InjectMocks
    private AdvertisementService advertisementService;

    private AdvertisementRequest advertisementRequest;
    private Advertisement advertisement;
    private AdvertisementResponse advertisementResponse;

    private static Map<String, Function> behaviorByType = new HashMap<>();

    static {
        behaviorByType.put("typ1", (s) -> {

            return null;
        });
        behaviorByType.put("typ2", (s) -> {

            return null;
        });
        behaviorByType.put("typ3", (s) -> {

            return null;
        });

    }


    @BeforeEach
    void setUp() {

        behaviorByType.get("typ3").apply(null);

        advertisementRequest = new AdvertisementRequest();
        advertisementRequest.setName("Test Advertisement");
        advertisementRequest.setCategory("ELECTRONICS");
        advertisementRequest.setSubcategory("Phones");
        advertisementRequest.setCost(1000);
        advertisementRequest.setDescription("Test Description");
        advertisementRequest.setAddress("Test Address");

        advertisement = Advertisement.builder()
                .id(1L)
                .name("Test Advertisement")
                .build();

        advertisementResponse = AdvertisementResponse.builder()
                .id(1L)
                .name("Test Advertisement")
                .category("ELECTRONICS")
                .subcategory("Phones")
                .cost(1000)
                .description("Test Description")
                .address("Test Address")
                .build();
    }

    @Test
    void createAdvertisement() {
        when(advertisementMapper.toAdvertisement(advertisementRequest)).thenReturn(advertisement);
        when(advertisementMapper.toAdvertisementResponse(advertisement)).thenReturn(advertisementResponse);

        advertisementService.createAdvertisement(advertisementRequest);

        verify(advertisementRepository).save(advertisement);
        verify(producerService).sendAdvertisement(advertisementResponse);
    }

    @Test
    void getAdvertisement() {
        when(advertisementRepository.findById(1L)).thenReturn(Optional.of(advertisement));
        when(advertisementMapper.toAdvertisementResponse(advertisement)).thenReturn(advertisementResponse);

        AdvertisementResponse result = advertisementService.getAdvertisement(1L);

        assertNotNull(result);
        assertEquals(advertisement.getId(), result.getId());
    }

    @Test
    void getAdvertisements() {
        when(advertisementRepository.findAll()).thenReturn(List.of(advertisement));
        when(advertisementMapper.toAdvertisementResponse(advertisement)).thenReturn(advertisementResponse);

        List<AdvertisementResponse> result = advertisementService.getAdvertisements(10);

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}