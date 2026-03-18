package com.newtome.newtomeapi.catalog.dto;

import java.math.BigDecimal;
<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> develop

public record CreateListingRequest(
        String title,
        String description,
        String color,
        BigDecimal price,
        String city,
        Long categoryId,
<<<<<<< HEAD
        String imageUrl
=======
        String imageUrl,
        List<String> imageUrls
>>>>>>> develop
) {}
