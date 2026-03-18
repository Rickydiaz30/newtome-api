package com.newtome.newtomeapi.catalog.dto;

import java.math.BigDecimal;
import java.time.Instant;
<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> develop

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
<<<<<<< HEAD
=======
        List<String> imageUrls,
>>>>>>> develop
        Long categoryId,
        String categoryName,
        Long ownerId,
        String ownerFirstName
) {

<<<<<<< HEAD
=======


>>>>>>> develop
}