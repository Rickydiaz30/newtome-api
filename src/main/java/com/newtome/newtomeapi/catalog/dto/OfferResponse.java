package com.newtome.newtomeapi.catalog.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record OfferResponse(
        Long id,
        BigDecimal amount,
        String message,
        String status,
        Instant createdAt,
        Long listingId,
        String listingTitle,
        Long buyerId,
        String buyerFirstName,
        String listingImageUrl
) {}