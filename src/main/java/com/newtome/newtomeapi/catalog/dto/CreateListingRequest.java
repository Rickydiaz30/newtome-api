package com.newtome.newtomeapi.catalog.dto;

import java.math.BigDecimal;

public record CreateListingRequest(
        String title,
        String description,
        String color,
        BigDecimal price,
        String city,
        Long categoryId,
        String imageUrl
) {}
