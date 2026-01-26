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
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category("Clothing", "Shirts, pants, shoes"));
            categoryRepository.save(new Category("Appliances", "Small and large appliances"));
            categoryRepository.save(new Category("Sporting Goods", "Sports and outdoor gear"));
            categoryRepository.save(new Category("Furniture", "Tables, chairs, couches"));
            categoryRepository.save(new Category("Tools", "Hand tools and power tools"));
        }
    }
}
