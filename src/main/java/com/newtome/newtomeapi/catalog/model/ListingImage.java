package com.newtome.newtomeapi.catalog.model;

import jakarta.persistence.*;

@Entity
public class ListingImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id")
    private Listing listing;

    public ListingImage() {}

    public ListingImage(Long id, String imageUrl, Listing listing) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.listing = listing;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Listing getListing() { return listing; }
    public void setListing(Listing listing) { this.listing = listing; }
}
