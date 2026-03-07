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

    @GetMapping("/search")
    public List<ListingResponse> searchListings(@RequestParam String query) {
        return listingService.search(null, null, null, query);
    }

    //    Get my listings
    @GetMapping("/mine")
    public List<ListingResponse> getMyListings(Authentication authentication) {
        String username = authentication.getName();
        return listingService.getMyListings(username);
    }

    //    Add a Listing
    @PostMapping
    public ListingResponse createListing(
            @RequestBody CreateListingRequest request,
            Authentication authentication
    ) {
        String username = authentication.getName();
        return listingService.createListing(request, username);
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

