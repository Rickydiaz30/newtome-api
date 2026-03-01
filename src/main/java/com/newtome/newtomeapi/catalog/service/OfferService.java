package com.newtome.newtomeapi.catalog.service;
import org.springframework.transaction.annotation.Transactional;
import com.newtome.newtomeapi.catalog.dto.CreateOfferRequest;
import com.newtome.newtomeapi.catalog.dto.OfferResponse;
import com.newtome.newtomeapi.catalog.model.Listing;
import com.newtome.newtomeapi.catalog.model.Offer;
import com.newtome.newtomeapi.catalog.repository.ListingRepository;
import com.newtome.newtomeapi.catalog.repository.OfferRepository;
import com.newtome.newtomeapi.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public OfferService(
            OfferRepository offerRepository,
            ListingRepository listingRepository,
            UserRepository userRepository
    ) {
        this.offerRepository = offerRepository;
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    public OfferResponse createOffer(
            Long listingId,
            CreateOfferRequest request,
            String buyerEmail
    ) {

        // 1️⃣ Find listing
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found: " + listingId));

        // 2️⃣ Listing must be ACTIVE
        if (!"ACTIVE".equals(listing.getStatus())) {
            throw new IllegalStateException("Cannot make offer on inactive listing.");
        }

        // 3️⃣ Find buyer
        var buyer = userRepository.findByEmail(buyerEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + buyerEmail));

        // 4️⃣ Buyer cannot offer on own listing
        if (listing.getOwner().getId().equals(buyer.getId())) {
            throw new IllegalStateException("You cannot make an offer on your own listing.");
        }

        // 5️⃣ Validate amount
        if (request.amount() == null || request.amount().signum() <= 0) {
            throw new IllegalArgumentException("Offer amount must be greater than zero.");
        }

        if (!listing.getStatus().equals("ACTIVE")) {
            throw new IllegalStateException("Cannot make an offer on a sold listing.");
        }


        // 6️⃣ Create offer
        Offer offer = new Offer();
        offer.setAmount(request.amount());
        offer.setMessage(request.message());
        offer.setStatus("PENDING");
        offer.setCreatedAt(Instant.now());
        offer.setListing(listing);
        offer.setBuyer(buyer);

        Offer saved = offerRepository.save(offer);

        return toResponse(saved);
    }

    private OfferResponse toResponse(Offer offer) {
        return new OfferResponse(
                offer.getId(),
                offer.getAmount(),
                offer.getMessage(),
                offer.getStatus(),
                offer.getCreatedAt(),
                offer.getListing().getId(),
                offer.getListing().getTitle(),
                offer.getBuyer().getId(),
                offer.getBuyer().getFirstName()
        );
    }

    public List<OfferResponse> getOffersForListing(Long listingId, String email) {

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));

        if (!listing.getOwner().getEmail().equals(email)) {
            throw new IllegalStateException("You are not the owner of this listing.");
        }

        List<Offer> offers = offerRepository
                .findByListingIdOrderByCreatedAtDesc(listingId);

        return offers.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public OfferResponse acceptOffer(Long listingId,
                                     Long offerId,
                                     String email) {

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));

        if (!listing.getOwner().getEmail().equals(email)) {
            throw new IllegalStateException("You are not the owner of this listing.");
        }

        Offer offerToAccept = offerRepository.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Offer not found"));

        if (!offerToAccept.getListing().getId().equals(listingId)) {
            throw new IllegalStateException("Offer does not belong to this listing.");
        }

        if (!listing.getOwner().getEmail().equals(email)) {
            throw new IllegalStateException("You are not the owner of this listing.");
        }


        offerToAccept.setStatus("ACCEPTED");

        List<Offer> offers = offerRepository.findByListingIdOrderByCreatedAtDesc(listingId);

        for (Offer offer : offers) {
            if (!offer.getId().equals(offerId)) {
                offer.setStatus("REJECTED");
            }
        }

        listing.setStatus("SOLD");

        offerRepository.saveAll(offers);
        listingRepository.save(listing);

        return toResponse(offerToAccept);
    }
}