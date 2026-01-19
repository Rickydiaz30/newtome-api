package com.newtome.newtomeapi.catalog.service;

import com.newtome.newtomeapi.catalog.dto.CreateListingRequest;
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

}
