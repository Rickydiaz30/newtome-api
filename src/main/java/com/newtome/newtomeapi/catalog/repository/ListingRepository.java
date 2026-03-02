package com.newtome.newtomeapi.catalog.repository;

import com.newtome.newtomeapi.catalog.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

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
}

