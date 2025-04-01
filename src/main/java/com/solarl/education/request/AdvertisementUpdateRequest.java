package com.solarl.education.request;

import com.solarl.education.validation.CapitalLetter;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementUpdateRequest {

    @CapitalLetter
    private String name;

    @Positive
    private Integer cost;

    private String description;
}
