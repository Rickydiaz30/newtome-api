package com.newtome.newtomeapi.catalog.repository;

import com.newtome.newtomeapi.catalog.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findByListingOwnerId(Long ownerId);

    List<Offer> findByBuyerId(Long buyerId);

    List<Offer> findByListingIdOrderByCreatedAtDesc(Long listingId);

    List<Offer> findByBuyerIdOrderByCreatedAtDesc(Long buyerId);
}