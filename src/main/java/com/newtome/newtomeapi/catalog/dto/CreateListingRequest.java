package com.newtome.newtomeapi.catalog.dto;

import java.math.BigDecimal;

import java.util.List;


public record CreateListingRequest(
        String title,
        String description,
        String color,
        BigDecimal price,
        String city,
        Long categoryId,

        String imageUrl,
        List<String> imageUrls

) {}
