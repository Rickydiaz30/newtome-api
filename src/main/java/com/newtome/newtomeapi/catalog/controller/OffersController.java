package com.newtome.newtomeapi.catalog.controller;

import com.newtome.newtomeapi.catalog.dto.OfferResponse;
import com.newtome.newtomeapi.catalog.service.OfferService;
import com.newtome.newtomeapi.common.ApiResponse;
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
    public ApiResponse<List<OfferResponse>> getMyOffers(Authentication authentication) {

        String username = authentication.getName();

        List<OfferResponse> offers = offerService.getMyOffers(username);

        return new ApiResponse<>(true, "My offers loaded", offers);
    }

    @GetMapping("/received")
    public ApiResponse<List<OfferResponse>> getOffersReceived(Authentication authentication) {

        String username = authentication.getName();

        List<OfferResponse> offers = offerService.getOffersReceived(username);

        return new ApiResponse<>(true, "Received offers loaded", offers);
    }

    @PatchMapping("/{offerId}/cancel")
    public ApiResponse<OfferResponse> cancelOffer(
            @PathVariable Long offerId,
            Authentication authentication
    ) {

        String username = authentication.getName();

        OfferResponse offer = offerService.cancelOffer(offerId, username);

        return new ApiResponse<>(true, "Offer cancelled", offer);
    }
}