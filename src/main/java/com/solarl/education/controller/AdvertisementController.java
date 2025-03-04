package com.solarl.education.controller;

import com.solarl.education.request.AdvertisementRequest;
import com.solarl.education.response.AdvertisementResponse;
import com.solarl.education.service.AdvertisementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("v1/advertisements")
@Tag(name = "Сервис объявлений", description = "API доступа к объявлениям")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping("/create")
    @Operation(summary = "Создание объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Успех"),
            @ApiResponse(responseCode = "400", description = "Неверно переданные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка работы сервиса")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public void createAdvertisement(
            @Parameter(description = "Запрос на отправку уведомления")
            @RequestBody AdvertisementRequest advertisementRequest) {
        advertisementService.createAdvertisement(advertisementRequest);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успех"),
            @ApiResponse(responseCode = "400", description = "Неверно переданные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка работы сервиса")
    })
    public AdvertisementResponse getAdvertisement(
            @Parameter(description = "Идентификатор объявления")
            @PathVariable Long id) {
        return advertisementService.getAdvertisement(id);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Редактирование объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успех"),
            @ApiResponse(responseCode = "400", description = "Неверно переданные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка работы сервиса")
    })
    public AdvertisementResponse updateAdvertisement(
            @Parameter(description = "Идентификатор объявления")
            @PathVariable Long id,
            @Parameter(description = "Объявление")
            @RequestBody AdvertisementRequest advertisementRequest) {
        return advertisementService.updateAdvertisement(id, advertisementRequest);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Редактирование объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успех"),
            @ApiResponse(responseCode = "400", description = "Неверно переданные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка работы сервиса")
    })
    public void deleteAdvertisement(
            @Parameter(description = "Идентификатор объявления")
            @PathVariable Long id) {
        advertisementService.deleteAdvertisement(id);
    }

}
