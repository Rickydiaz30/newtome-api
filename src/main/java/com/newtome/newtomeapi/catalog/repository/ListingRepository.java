package com.newtome.newtomeapi.catalog.repository;

import com.newtome.newtomeapi.catalog.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> findByOwnerIdOrderByCreatedAtDesc(Long ownerId);

    List<Listing> findAllByOrderByCreatedAtDesc();

    List<Listing> findByCategoryIdOrderByCreatedAtDesc(Long categoryId);

    List<Listing> findByTitleContainingIgnoreCase(String title);

    List<Listing> findByCityIgnoreCaseOrderByCreatedAtDesc(String city);

    List<Listing> findByColorIgnoreCaseOrderByCreatedAtDesc(String color);

    List<Listing> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByCreatedAtDesc(
            String titleQuery,
            String descriptionQuery
    );

    // Marketplace ordering: ACTIVE first, then SOLD, newest first
    @Query("""
        SELECT l
        FROM Listing l
        ORDER BY
        CASE
            WHEN l.status = 'ACTIVE' THEN 0
            WHEN l.status = 'SOLD' THEN 1
            ELSE 2
        END,
        l.createdAt DESC
    """)
    List<Listing> findMarketplaceListings();
}