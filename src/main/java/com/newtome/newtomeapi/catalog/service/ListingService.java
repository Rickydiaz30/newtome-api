package com.newtome.newtomeapi.catalog.service;

import com.newtome.newtomeapi.catalog.dto.CreateListingRequest;
import com.newtome.newtomeapi.catalog.dto.UpdateListingRequest;
import com.newtome.newtomeapi.catalog.model.Category;
import com.newtome.newtomeapi.catalog.model.Listing;
import com.newtome.newtomeapi.catalog.repository.CategoryRepository;
import com.newtome.newtomeapi.catalog.repository.ListingRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final CategoryRepository categoryRepository;

    public ListingService(ListingRepository listingRepository, CategoryRepository categoryRepository) {
        this.listingRepository = listingRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public List<Listing> search(Long categoryId, String city, String color, String query) {
        if (categoryId != null) {
            return listingRepository.findByCategoryIdOrderByCreatedAtDesc(categoryId);
        }
        if (city != null && !city.isBlank()) {
            return listingRepository.findByCityIgnoreCaseOrderByCreatedAtDesc(city);
        }
        if (color != null && !color.isBlank()) {
            return listingRepository.findByColorIgnoreCaseOrderByCreatedAtDesc(color);
        }
        if (query != null && !query.isBlank()) {
            return listingRepository
                    .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByCreatedAtDesc(query, query);
        }
        return listingRepository.findAllByOrderByCreatedAtDesc();
    }

//    Add a new listing
    public Listing createListing(CreateListingRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + request.categoryId()));

        Listing listing = new Listing(
                request.title(),
                request.description(),
                request.color(),
                request.price(),
                request.city(),
                "AVAILABLE",
                Instant.now(),
                category
        );

        return listingRepository.save(listing);
    }

//    Update a Listing with Patch
    public Listing patchListing(Long listingId, UpdateListingRequest request) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found: " + listingId));

        // Update fields only if they are provided
        if (request.title() != null && !request.title().isBlank()) {
            listing.setTitle(request.title());
        }
        if (request.description() != null && !request.description().isBlank()) {
            listing.setDescription(request.description());
        }
        if (request.color() != null && !request.color().isBlank()) {
            listing.setColor(request.color());
        }
        if (request.price() != null) {
            listing.setPrice(request.price());
        }
        if (request.city() != null && !request.city().isBlank()) {
            listing.setCity(request.city());
        }
        if (request.status() != null && !request.status().isBlank()) {
            listing.setStatus(request.status());
        }

        // Update category if provided
        if (request.categoryId() != null) {
            Category category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found: " + request.categoryId()));
            listing.setCategory(category);
        }

        return listingRepository.save(listing);
    }


    //    Delete a Listing
    public void deleteListing(Long listingId) {
        if (!listingRepository.existsById(listingId)) {
            throw new IllegalArgumentException("Listing not found: " + listingId);
        }
        listingRepository.deleteById(listingId);
    }


}
