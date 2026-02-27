package com.solarl.education.controller;

import com.solarl.education.request.AdvertisementRequest;
import com.solarl.education.response.AdvertisementResponse;
import com.solarl.education.service.AdvertisementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/advertisements")
@RequiredArgsConstructor
@Tag(name = "Сервис объявлений", description = "API доступа к объявлениям")
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    @PostConstruct
    public void init() {
        System.out.println("Создали бин AdvertisementController");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Создано"),
            @ApiResponse(responseCode = "400", description = "неверно переданные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка работы сервиса")
    })
    public void createAdvertisement(
            @Parameter(description = "Модель для создания данных")
            @RequestBody AdvertisementRequest advertisementRequest
    ) {
        advertisementService.createAdvertisement(advertisementRequest);
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Создано"),
            @ApiResponse(responseCode = "400", description = "неверно переданные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка работы сервиса")
    })
    public AdvertisementResponse getAdvertisement(
            @Parameter(description = "Идентификатор объявления")
            @PathVariable Long id) {
        return advertisementService.getAdvertisementById(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "Изменение объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Создано"),
            @ApiResponse(responseCode = "400", description = "неверно переданные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка работы сервиса")
    })
    public AdvertisementResponse updateAdvertisement(
            @Parameter(description = "Идентификатор объявления")
            @PathVariable Long id,
            @Parameter(description = "Модель для изменения данных")
            @RequestBody AdvertisementRequest advertisementRequest) {
        return advertisementService.updateAdvertisementById(id, advertisementRequest);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Создано"),
            @ApiResponse(responseCode = "400", description = "неверно переданные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка работы сервиса")
    })
    public void deleteAdvertisement(
            @Parameter(description = "Идентификатор объявления")
            @PathVariable Long id
    ) {
        advertisementService.deleteAdvertisementById(id);
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Удаляем бин AdvertisementController");
    }
}
