package com.solarl.education.request;

import com.solarl.education.validation.CapitalLetter;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementRequest {
    @NotBlank(message = "Наименование должно быть передано")
    @CapitalLetter
    private String name;
    @NotBlank
    private String category;
    @NotBlank
    private String subcategory;
    @Positive
    private Integer cost;
    private String description;
    @PastOrPresent
    private LocalDateTime createDateTime;
}
