package com.newtome.newtomeapi.catalog.controller;

import com.newtome.newtomeapi.catalog.dto.OfferResponse;
import com.newtome.newtomeapi.catalog.service.OfferService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OffersController {

    private final OfferService offerService;

    public OffersController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/mine")
    public List<OfferResponse> getMyOffers(Authentication authentication) {
        String email = authentication.getName();
        return offerService.getMyOffers(email);
    }

    @PatchMapping("/{offerId}/cancel")
    public OfferResponse cancelOffer(
            @PathVariable Long offerId,
            Authentication authentication
    ) {
        String email = authentication.getName();
        return offerService.cancelOffer(offerId, email);
    }
}