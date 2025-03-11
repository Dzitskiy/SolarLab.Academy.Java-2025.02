package com.solarl.education.mapper;

import com.solarl.education.entity.Advertisement;
import com.solarl.education.request.AdvertisementRequest;
import com.solarl.education.response.AdvertisementResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdvertisementMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDateTime", ignore = true)
    @Mapping(target = "seller", constant = "user1")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "name", source = "name")
    Advertisement toAdvertisement(AdvertisementRequest advertisementRequest);

    AdvertisementResponse toAdvertisementResponse(Advertisement advertisement);

    List<AdvertisementResponse> toListAdvertisementResponse(List<Advertisement> items);

    @AfterMapping
    default void setDefaultValues(@MappingTarget Advertisement entity) {
        if (entity.getSubcategory() == null || entity.getSubcategory().isEmpty()) {
            entity.setSubcategory("General");
        }
    }
}
