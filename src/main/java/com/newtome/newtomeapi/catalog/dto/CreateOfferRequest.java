package com.newtome.newtomeapi.catalog.dto;

import java.math.BigDecimal;

public record CreateOfferRequest(
        BigDecimal amount,
        String message
) {}