package com.newtome.newtomeapi.catalog.dto;

import java.math.BigDecimal;

public record CreateOfferRequest(
        Long listingId,
        BigDecimal amount,
        String message
) {}