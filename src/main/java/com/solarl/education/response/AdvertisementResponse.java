package com.solarl.education.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementResponse {
    private Long id;
    private String name;
    private String category;
    private String subcategory;
    private Integer cost;
    private String description;
    private String address;
    private LocalDateTime createDateTime;
}
