package com.newtome.newtomeapi.catalog.repository;

import com.newtome.newtomeapi.catalog.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("""
        SELECT o FROM Offer o
        JOIN FETCH o.listing
        JOIN FETCH o.buyer
        WHERE o.buyer.id = :buyerId
        ORDER BY o.createdAt DESC
    """)
    List<Offer> findByBuyerIdOrderByCreatedAtDesc(@Param("buyerId") Long buyerId);

    @Query("""
        SELECT o FROM Offer o
        JOIN FETCH o.listing
        JOIN FETCH o.buyer
        WHERE o.listing.owner.id = :ownerId
        ORDER BY o.createdAt DESC
    """)
    List<Offer> findByListingOwnerIdOrderByCreatedAtDesc(@Param("ownerId") Long ownerId);

    @Query("""
        SELECT o FROM Offer o
        JOIN FETCH o.listing
        JOIN FETCH o.buyer
        WHERE o.listing.id = :listingId
        ORDER BY o.createdAt DESC
    """)
    List<Offer> findByListingIdOrderByCreatedAtDesc(@Param("listingId") Long listingId);
}