package com.newtome.newtomeapi.catalog.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record ListingResponse(
        Long id,
        String title,
        String description,
        String color,
        BigDecimal price,
        String city,
        String status,
        Instant createdAt,
        String imageUrl,
        List<String> imageUrls,
        Long categoryId,
        String categoryName,
        Long ownerId,
        String ownerFirstName
) {



}