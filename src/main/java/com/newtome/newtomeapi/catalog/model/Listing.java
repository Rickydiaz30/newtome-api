package com.newtome.newtomeapi.catalog.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String color;
    private BigDecimal price;
    private String city;
    private String status;
    private Instant createdAt;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Listing(){}

    public Listing(String title, String description, String color, BigDecimal price, String city, String status, Instant createdAt, Category category) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.price = price;
        this.city = city;
        this.status = status;
        this.createdAt = createdAt;
        this.category = category;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getColor() {
        return color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCity() {
        return city;
    }

    public String getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Listing{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                ", city='" + city + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", categoryId=" + (category != null ? category.getId() : null) +
                '}';
    }

}
