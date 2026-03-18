package com.newtome.newtomeapi.common;

import com.newtome.newtomeapi.catalog.model.Category;
import com.newtome.newtomeapi.catalog.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CategorySeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public CategorySeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {

        seed("Clothing", "Shirts, pants, shoes");
        seed("Appliances", "Small and large appliances");
        seed("Sporting Goods", "Sports and outdoor gear");
        seed("Furniture", "Tables, chairs, couches");
        seed("Tools", "Hand tools and power tools");
        seed("Electronics", "TV's, sound systems, etc...");
        seed("Musical Instruments", "Guitars, amps, drums, keyboards, and other music equipment");
        seed("Automotive", "Cars, trucks, parts, and accessories");
        seed("Books", "Fiction, non-fiction, textbooks");
        seed("Toys & Games", "Board games, kids toys, collectibles");
        seed("Home & Garden", "Outdoor furniture, plants, decor");
        seed("Baby & Kids", "Strollers, cribs, baby gear");
        seed("Health & Fitness", "Gym equipment, supplements, wellness items");
        seed("Office Supplies", "Desks, chairs, office electronics");
        seed("Pet Supplies", "Food, cages, accessories");
    }

    private void seed(String name, String description) {
        if (!categoryRepository.existsByName(name)) {
            categoryRepository.save(new Category(name, description));
        }
    }

}
