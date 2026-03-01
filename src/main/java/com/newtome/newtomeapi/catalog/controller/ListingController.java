package com.newtome.newtomeapi.catalog.controller;

import com.newtome.newtomeapi.catalog.dto.CreateListingRequest;
import com.newtome.newtomeapi.catalog.dto.ListingResponse;
import com.newtome.newtomeapi.catalog.dto.UpdateListingRequest;
import com.newtome.newtomeapi.catalog.service.ListingService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

//    Get all Listings
    @GetMapping
    public List<ListingResponse> getListings(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String query
    ) {
        return listingService.search(categoryId, city, color, query);
    }

    //    Add a Listing
    @PostMapping
    public ListingResponse createListing(
            @RequestBody CreateListingRequest request,
            Authentication authentication
    ) {
        String email = authentication.getName();
        return listingService.createListing(request, email);
    }

//    Update a Listing
    @PatchMapping("/{id}")
    public ListingResponse patchListing(@PathVariable Long id, @RequestBody UpdateListingRequest request) {
        return listingService.patchListing(id, request);
    }


    //    Delete a Listing
    @DeleteMapping("/{id}")
    public void deleteListing(@PathVariable Long id) {
        listingService.deleteListing(id);
    }

}

