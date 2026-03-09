package com.newtome.newtomeapi.catalog.controller;

import com.newtome.newtomeapi.catalog.dto.CreateListingRequest;
import com.newtome.newtomeapi.catalog.dto.ListingResponse;
import com.newtome.newtomeapi.catalog.dto.UpdateListingRequest;
import com.newtome.newtomeapi.catalog.service.ListingService;
import com.newtome.newtomeapi.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ApiResponse<List<ListingResponse>> getMyListings(Authentication authentication) {
        String username = authentication.getName();
        List<ListingResponse> listings = listingService.getMyListings(username);
        return new ApiResponse<>(true, "Listings Loaded", listings);
    }

    //    Add a Listing
    @PostMapping
    public ResponseEntity<ApiResponse<ListingResponse>> createListing(
            @RequestBody CreateListingRequest request,
            Authentication authentication
    ) {

        String username = authentication.getName();

        ListingResponse listing = listingService.createListing(request, username);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Listing created", listing));
    }

    //    Update a Listing
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ListingResponse>> patchListing(
            @PathVariable Long id,
            @RequestBody UpdateListingRequest request
    ) {

        ListingResponse listing = listingService.patchListing(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Listing updated", listing)
        );
    }


    //    Delete a Listing
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {

        listingService.deleteListing(id);

        return ResponseEntity.noContent().build();
    }

}

