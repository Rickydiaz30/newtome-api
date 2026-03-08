package com.newtome.newtomeapi.catalog.controller;

import com.newtome.newtomeapi.catalog.dto.CreateOfferRequest;
import com.newtome.newtomeapi.catalog.dto.OfferResponse;
import com.newtome.newtomeapi.catalog.service.OfferService;
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
    public List<OfferResponse> getOffersForListing(
            @PathVariable Long listingId,
            Authentication authentication
    ) {
        String username = authentication.getName();
        return offerService.getOffersForListing(listingId, username);
    }

//    @GetMapping("/received")
//    public List<OfferResponse> getOffersReceived(Authentication authentication) {
//        String username = authentication.getName();
//        return offerService.getOffersReceived(username);
//    }

    @PostMapping
    public OfferResponse createOffer(
            @PathVariable Long listingId,
            @RequestBody CreateOfferRequest request,
            Authentication authentication
    ) {
        String buyerEmail = authentication.getName();
        return offerService.createOffer(listingId, request, buyerEmail);
    }

    @PostMapping("/{offerId}/accept")
    public OfferResponse acceptOffer(
            @PathVariable Long listingId,
            @PathVariable Long offerId,
            Authentication authentication
    ) {
        String email = authentication.getName();
        return offerService.acceptOffer(listingId, offerId, email);
    }

    @PostMapping("/{offerId}/reject")
    public OfferResponse rejectOffer(
            @PathVariable Long listingId,
            @PathVariable Long offerId,
            Authentication authentication
    ) {
        String email = authentication.getName();
        return offerService.rejectOffer(listingId, offerId, email);
    }
}