package com.solarl.education.controller;

import com.solarl.education.request.AdvertisementRequest;
import com.solarl.education.response.AdvertisementResponse;
import com.solarl.education.service.AdvertisementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/advertisements")
@Tag(name = "Сервис объявлений", description = "API доступа к объявлениям")
@Validated
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping()
    @Operation(summary = "Создание объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Успех"),
            @ApiResponse(responseCode = "400", description = "Неверно переданные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка работы сервиса")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public void createAdvertisement(
            @Parameter(description = "Запрос на отправку уведомления")
            @RequestBody @Valid AdvertisementRequest advertisementRequest) {
        advertisementService.createAdvertisement(advertisementRequest);

    }

    @GetMapping()
    @Operation(summary = "Постраничное получение объявлений")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успех"),
            @ApiResponse(responseCode = "400", description = "Неверно переданные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка работы сервиса")
    })
    public List<AdvertisementResponse> getAdvertisementPage(
            @Parameter(description = "Количество элементов на странице")
            @RequestParam(value = "limit", required = false) @Positive Integer limit) {
        return advertisementService.getAdvertisements(limit);
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успех"),
            @ApiResponse(responseCode = "400", description = "Неверно переданные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка работы сервиса")
    })
    public AdvertisementResponse getAdvertisement(
            @Parameter(description = "Идентификатор объявления")
            @PathVariable @PositiveOrZero Long id) {
        return advertisementService.getAdvertisement(id);
    }

    @PostMapping("/add/basket/{id}")
    @Operation(summary = "Добавление объявления в корзину")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успех"),
            @ApiResponse(responseCode = "400", description = "Неверно переданные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка работы сервиса")
    })
    public AdvertisementResponse addAdvertisementToBasket(
            @Parameter(description = "Идентификатор объявления")
            @PathVariable @PositiveOrZero Long id) {
        return advertisementService.addAdvertisementToBasket(id);
    }

    @PostMapping("/pay/basket")
    @Operation(summary = "Оплата объявлений из корзины")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успех"),
            @ApiResponse(responseCode = "400", description = "Неверно переданные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка работы сервиса")
    })
    public List<AdvertisementResponse> payPurchasesFromBasket(
            @Parameter(description = "Идентификаторы объявлений")
            @RequestParam List<Long> ids) {
        return advertisementService.payPurchasesFromBasket(ids);
    }

    @PutMapping("{id}")
    @Operation(summary = "Редактирование объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успех"),
            @ApiResponse(responseCode = "400", description = "Неверно переданные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка работы сервиса")
    })
    public AdvertisementResponse updateAdvertisement(
            @Parameter(description = "Идентификатор объявления")
            @PathVariable @PositiveOrZero Long id,
            @Parameter(description = "Объявление")
            @RequestBody AdvertisementRequest advertisementRequest) {
        return advertisementService.updateAdvertisement(id, advertisementRequest);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    @Operation(summary = "Удаление объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успех"),
            @ApiResponse(responseCode = "400", description = "Неверно переданные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка работы сервиса")
    })
    public void deleteAdvertisement(
            @Parameter(description = "Идентификатор объявления")
            @PathVariable @PositiveOrZero Long id) {
        advertisementService.deleteAdvertisement(id);
    }

}
