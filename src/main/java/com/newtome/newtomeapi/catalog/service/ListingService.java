package com.newtome.newtomeapi.catalog.service;

import com.newtome.newtomeapi.catalog.dto.CreateListingRequest;
import com.newtome.newtomeapi.catalog.dto.ListingResponse;
import com.newtome.newtomeapi.catalog.dto.UpdateListingRequest;
import com.newtome.newtomeapi.catalog.model.Category;
import com.newtome.newtomeapi.catalog.model.Listing;
import com.newtome.newtomeapi.catalog.repository.CategoryRepository;
import com.newtome.newtomeapi.catalog.repository.ListingRepository;
import com.newtome.newtomeapi.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ListingService {

// These are field declarations. This isn't injecting anything. This is just saying this class
//    will have a listing, category, and user dependencies.
    private final ListingRepository listingRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

// This is a constructor that spring will use to create the ListingService bean.
    public ListingService(ListingRepository listingRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.listingRepository = listingRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public List<ListingResponse> search(Long categoryId, String city, String color, String query) {

        List<Listing> listings;

        if (categoryId != null) {
            listings = listingRepository.findByCategoryIdOrderByCreatedAtDesc(categoryId);
        }
        else if (city != null && !city.isBlank()) {
            listings = listingRepository.findByCityIgnoreCaseOrderByCreatedAtDesc(city);
        }
        else if (color != null && !color.isBlank()) {
            listings = listingRepository.findByColorIgnoreCaseOrderByCreatedAtDesc(color);
        }
        else if (query != null && !query.isBlank()) {
            listings = listingRepository
                    .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByCreatedAtDesc(query, query);
        }
        else {
            listings = listingRepository.findAllByOrderByCreatedAtDesc();
        }

        return listings
                .stream()
                .map(this::toResponse)
                .toList();
    }


//    Update a Listing with Patch
    public ListingResponse patchListing(Long listingId, UpdateListingRequest request) {
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

        Listing saved = listingRepository.save(listing);
        return toResponse(saved);
    }

    // Get my listings
    public List<ListingResponse> getMyListings(String username) {

        var user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        return listingRepository.findByOwnerIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }


    //    Delete a Listing
    public void deleteListing(Long listingId) {
        if (!listingRepository.existsById(listingId)) {
            throw new IllegalArgumentException("Listing not found: " + listingId);
        }
        listingRepository.deleteById(listingId);
    }

    // Create a listing
    public ListingResponse createListing(CreateListingRequest request, String username) {

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + request.categoryId()));

        var user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        Listing listing = new Listing();

        listing.setTitle(request.title());
        listing.setDescription(request.description());
        listing.setColor(request.color());
        listing.setPrice(request.price());
        listing.setCity(request.city());
        listing.setStatus("ACTIVE");
        listing.setCreatedAt(Instant.now());
        listing.setImageUrl(request.imageUrl());
        listing.setCategory(category);

        listing.setOwner(user);

        Listing saved = listingRepository.save(listing);
        return toResponse(saved);
    }

    private ListingResponse toResponse(Listing listing) {
        return new ListingResponse(
                listing.getId(),
                listing.getTitle(),
                listing.getDescription(),
                listing.getColor(),
                listing.getPrice(),
                listing.getCity(),
                listing.getStatus(),
                listing.getCreatedAt(),
                listing.getImageUrl(),
                listing.getCategory().getId(),
                listing.getCategory().getName(),
                listing.getOwner().getId(),
                listing.getOwner().getFirstName()
        );
    }

}
