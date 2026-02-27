package com.solarl.education.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AdvertisementResponse {
    private Long id;
    private String name;
    private String category;
    private String subcategory;
    private Integer cost;
    private String address;
    private String description;
    private LocalDateTime createDateTime;
}
