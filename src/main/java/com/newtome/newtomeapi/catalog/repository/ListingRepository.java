package com.newtome.newtomeapi.catalog.repository;

import com.newtome.newtomeapi.catalog.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    // Seller listings
    @Query("""
    SELECT l FROM Listing l
    JOIN FETCH l.owner
    JOIN FETCH l.category
    WHERE l.owner.id = :ownerId
    ORDER BY l.createdAt DESC
    """)
    List<Listing> findByOwnerIdOrderByCreatedAtDesc(@Param("ownerId") Long ownerId);


    // Marketplace listings (default page load)
    @Query("""
    SELECT l
    FROM Listing l
    JOIN FETCH l.category
    JOIN FETCH l.owner
    ORDER BY
    CASE
        WHEN l.status = 'ACTIVE' THEN 0
        WHEN l.status = 'SOLD' THEN 1
        ELSE 2
    END,
    l.createdAt DESC
    """)
        List<Listing> findMarketplaceListings();


    // Marketplace search
        @Query("""
    SELECT l
    FROM Listing l
    JOIN FETCH l.category
    JOIN FETCH l.owner
    WHERE
        LOWER(l.title) LIKE LOWER(CONCAT('%', :query, '%'))
        OR LOWER(l.description) LIKE LOWER(CONCAT('%', :query, '%'))
        OR LOWER(l.city) LIKE LOWER(CONCAT('%', :query, '%'))
        OR LOWER(l.category.name) LIKE LOWER(CONCAT('%', :query, '%'))
    ORDER BY
    CASE
        WHEN l.status = 'ACTIVE' THEN 0
        WHEN l.status = 'SOLD' THEN 1
        ELSE 2
    END,
    l.createdAt DESC
    """)
        List<Listing> searchMarketplaceListings(String query);
    }