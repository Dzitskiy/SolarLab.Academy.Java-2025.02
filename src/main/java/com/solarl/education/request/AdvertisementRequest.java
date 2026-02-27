package com.solarl.education.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdvertisementRequest {
    private String name;
    private String category;
    private String subcategory;
    private Integer cost;
    private String address;
    private String description;
    private LocalDateTime createDateTime;
}
