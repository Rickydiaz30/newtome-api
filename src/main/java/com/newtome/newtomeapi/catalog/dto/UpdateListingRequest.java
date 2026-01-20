package com.newtome.newtomeapi.catalog.dto;

import java.math.BigDecimal;

public record UpdateListingRequest(
        String title,
        String description,
        String color,
        BigDecimal price,
        String city,
        String status,
        Long categoryId
) {
}
