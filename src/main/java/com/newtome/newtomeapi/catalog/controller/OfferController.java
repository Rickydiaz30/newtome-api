package com.newtome.newtomeapi.catalog.controller;

import com.newtome.newtomeapi.catalog.dto.CreateOfferRequest;
import com.newtome.newtomeapi.catalog.dto.OfferResponse;
import com.newtome.newtomeapi.catalog.service.OfferService;
import com.newtome.newtomeapi.common.ApiResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listings/{listingId}/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public ApiResponse<List<OfferResponse>> getOffersForListing(
            @PathVariable Long listingId,
            Authentication authentication
    ) {
        String username = authentication.getName();

        List<OfferResponse> offers =
                offerService.getOffersForListing(listingId, username);

        return new ApiResponse<>(true, "Offers loaded", offers);
    }

    @PostMapping
    public ApiResponse<OfferResponse> createOffer(
            @PathVariable Long listingId,
            @RequestBody CreateOfferRequest request,
            Authentication authentication
    ) {
        String username = authentication.getName();

        OfferResponse offer =
                offerService.createOffer(listingId, request, username);

        return new ApiResponse<>(true, "Offer created", offer);
    }

    @PostMapping("/{offerId}/accept")
    public ApiResponse<OfferResponse> acceptOffer(
            @PathVariable Long listingId,
            @PathVariable Long offerId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        OfferResponse offer =
                offerService.acceptOffer(listingId, offerId, email);

        return new ApiResponse<>(true, "Offer accepted", offer);
    }

    @PostMapping("/{offerId}/reject")
    public ApiResponse<OfferResponse> rejectOffer(
            @PathVariable Long listingId,
            @PathVariable Long offerId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        OfferResponse offer =
                offerService.rejectOffer(listingId, offerId, email);

        return new ApiResponse<>(true, "Offer rejected", offer);
    }
}